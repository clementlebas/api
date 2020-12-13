import React from "react";
import ReactDOM from "react-dom";
import "../style/main.css";
import Inscription from "./inscription";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = { users: [], inscription: false };
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

  onSubmit() {
    const myHeaders = new Headers({
      "Content-Type": "application/json",
      Accept: "application/json",
      "Access-Control-Allow-Headers": "*",
    });

    const formData = new FormData();

    const uname = document.getElementById("uname").value;
    const password = document.getElementById("password").value;

    formData.append("uname", uname);
    formData.append("password", password);

    fetch("/api/login/", {
      method: "POST",
      headers: myHeaders,
      credentials: "same-origin",
      mode: "same-origin",
      body: JSON.stringify({ uname: uname, password: password }),
    }).then((response) => {
      if (response.ok) {
        response.json().then(function (data) {
          console.log("login data", data);
          const myHeaders = new Headers({
            "Content-Type": "text/html; charset=utf-8",
            Accept: "application/json, text/javascript, */*; q=0.01",
            Authorization: `Bearer ${data.token}`,
          });

          const params = {
            method: "GET",
            headers: myHeaders,
          };
          fetch("/api/sondage", params).then((response) => {
            if (response.ok) {
              response.json().then(function (data) {
                console.log("data", data);
              });
              window.location = "http://localhost:4000/api/sondage/";
              // this.setState({ users: response.json() });
              // console.log("users", this.state.users);
            }
          });
        });
        // this.setState({ users: response.json() });
        // console.log("users", this.state.users);
      }
    });
  }

  render() {
    return (
      <div className="app">
        <div
        //  action="/api/login" method="post"
        >
          <div class="container">
            <label for="uname">
              <b>Username</b>
            </label>
            <input
              type="text"
              placeholder="Enter Username"
              name="uname"
              id="uname"
              required
            />

            <label for="password">
              <b>Password</b>
            </label>
            <input
              type="password"
              placeholder="Enter Password"
              name="password"
              id="password"
              required
            />

            <button
              // type="submit"
              onClick={this.onSubmit}
            >
              Login
            </button>
          </div>

          <div class="container">
            <button
              type="button"
              onClick={() => {
                console.log("this.state.inscription", this.state.inscription);
                this.setState({
                  inscription: !this.state.inscription,
                });
              }}
            >
              Create user
            </button>

            {this.state.inscription ? <Inscription /> : null}
          </div>
        </div>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.getElementById("react"));
