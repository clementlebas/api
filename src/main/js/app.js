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

  render() {
    return (
      <div className="app">
        <form action="/api/login" method="post">
          <div class="container">
            <label for="uname">
              <b>Username</b>
            </label>
            <input
              type="text"
              placeholder="Enter Username"
              name="uname"
              required
            />

            <label for="password">
              <b>Password</b>
            </label>
            <input
              type="password"
              placeholder="Enter Password"
              name="password"
              required
            />

            <button type="submit">Login</button>
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
        </form>
      </div>
    );
  }
}

ReactDOM.render(<App />, document.getElementById("react"));
