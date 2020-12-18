import React from "react";
import ReactDOM from "react-dom";
import "../resources/static/main.css";
import Inscription from "./inscription";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = { users: [], userCreated: false };
  }

  componentDidMount() {
    const myHeaders = new Headers({
      "Content-Type": "application/json; charset=UTF-8",
      Accept: "application/json, text/javascript, */*; q=0.01",
    });

    const params = {
      method: "GET",
      headers: myHeaders,
    };
    fetch("/api/users", params).then((response) => {
      if (response.ok) {
        response.json().then(function (data) {
          console.log("data", data);
        });
        // this.setState({ users: response.json() });
        // console.log("users", this.state.users);
      }
    });
  }

  onSubmitConnection() {
    const myHeaders = new Headers({
      "Content-Type": "application/json",
      Accept: "application/json",
      "Access-Control-Allow-Headers": "*",
    });

    const uname = document.getElementById("uname").value;
    const password = document.getElementById("password").value;

    fetch("/api/login/", {
      method: "POST",
      headers: myHeaders,
      credentials: "same-origin",
      mode: "same-origin",
      body: JSON.stringify({ uname: uname, password: password }),
    }).then((response) => {
      if (response.ok) {
        response.json().then(function (responseData) {
          console.log("login data", responseData);
          if (responseData.token) {
            window.location.href = "http://localhost:4000/api/survey/";
            sessionStorage.setItem('token', responseData.token);
          }
            else
              window.location.href = "http://localhost:4000/api/";
        });
      }
    });
  }

  render() {
    return (
      <div className="app">
        <div className="wrapper">

          <div class="loginContainer">
          <h1>Connexion</h1>
            <input
              type="text"
              placeholder="Username"
              className="inputIndex"
              name="uname"
              id="uname"
              required
            />
            <input
              type="password"
              placeholder="Password"
              placeholder="Password"
              className="inputIndex"
              name="password"
              className="inputIndex"
              id="password"
              required
            />

            <button onClick={this.onSubmitConnection}>
              Login
            </button>
          </div>

          <div class="loginContainer">
            <Inscription />
            {this.state.userCreated ? "utilisateur créé" : ""}
          </div>
        </div>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.getElementById("react"));
