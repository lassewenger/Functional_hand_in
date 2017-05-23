import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import WebSocket
import List
import Json.Decode as Decode
import Json.Encode as Encode

main : Program Never Model Msg
main =
  program
  { init = init
  , view = view
  , update = update
  , subscriptions = subscriptions
  }

 -- MODEL
type alias Model =
  { chatMessage : List String
  , userMessage : String
  , username : String
  }

init : (Model, Cmd Msg)
init =
  ( Model [] "" ""
  , Cmd.none
  )

--type alias ChatMessage =
  --{ command: String
  --, content: String
  --}

-- UPDATE
type Msg
  = PostChatMessage
  | UpdateUserMessage String
  | UpdateUserName String
  | NewChatMessage String
  | PostLogin

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
  case msg of
    PostChatMessage ->
      let
        message =
          Encode.object [ ("command", Encode.string "send")
                 , ("content", Encode.string model.userMessage)
                 ]
      in
        { model | userMessage = "" } ! [WebSocket.send "ws://localhost:3000/" (Encode.encode 0 message) ]

    PostLogin ->
      let
        userName =
          Encode.object [ ("command", Encode.string "login")
                 , ("content", Encode.string model.username)
                 ]
      in
        { model | username = "" } ! [WebSocket.send "ws://localhost:3000/" (Encode.encode 0 userName) ]

    UpdateUserMessage message ->
      { model | userMessage = message } ! []

    UpdateUserName message ->
      { model | username = message } ! []

    NewChatMessage message -> --Case message of (login or send)
        { model | chatMessage = jsonToString (Decode.decodeString (Decode.field "content" Decode.string) message) :: model.chatMessage } ! []

jsonToString : Result String String -> String
jsonToString result =
  case result of
    Ok result -> result
    Err result -> result

-- VIEW
view : Model -> Html Msg
view model =
  div []
    [ input [ placeholder "Username"
    , autofocus True
    , value model.username
    , onInput UpdateUserName
    ] []
    , button [ onClick PostLogin ] [ text "Login" ]
    , div [] (List.reverse (List.map showMessage  model.chatMessage))
    , input [ placeholder "Message..."
            , autofocus True
            , value model.userMessage
            , onInput UpdateUserMessage
            ] []
    , button [ onClick PostChatMessage ] [ text "Submit" ]
  ]

--chatBoxStyle : Attribute
--chatBoxStyle =
  --style
    --[ ("width", "300px")
    --, ("border", "25px", "solid")
    --, ("padding" "10px")
    --, ("margin" "25px")
    --]

showMessage : String -> Html msg
showMessage msg =
  div [] [text msg]

 -- SUBSCRIPTIONS
subscriptions : Model -> Sub Msg
subscriptions model =
  WebSocket.listen "ws://localhost:3000" NewChatMessage
