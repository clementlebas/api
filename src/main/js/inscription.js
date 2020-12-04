import React from "react";
import ReactDOM from "react-dom";
import "../style/main.css";

class Inscrption extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="app">
        <form
          action="/api/inscription"
          method="post"
          enctype="application/x-www-form-urlencoded"
        >
          <div class="container">
            <label for="uname">
              <b>Entrer un nom d'utilisateur</b>
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
            <label for="confirmPassword">
              <b>Confirm password</b>
            </label>
            <input
              type="confirmPassword"
              placeholder="Enter Password"
              name="confirmPassword"
              required
            />

            <button type="submit">Créer</button>
          </div>
        </form>
      </div>
    );
  }
}

export default Inscrption;
