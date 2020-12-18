import React from "react";
import ReactDOM from "react-dom";
import "../resources/static/main.css";

class Inscrption extends React.Component {
  constructor(props) {
    super(props);
  }

  validatePassword() {
    const password = document.getElementById("createpassword");
    const confirm_password = document.getElementById("confirm_password");
    if (password.value != confirm_password.value) {
      confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
      confirm_password.setCustomValidity("");
    }
  }

  // onSubmit() {
  //   // const myForm = new FormData(document.getElementById("signupForm"));

  //   const myHeaders = new Headers({
  //     "Content-Type": "application/json; charset=UTF-8",
  //     Accept: "application/json, text/javascript, */*; q=0.01",
  //     "Access-Control-Allow-Headers": "*",
  //   });

  //   const formData = new FormData();

  //   const uname = document.getElementById("uname");
  //   const password = document.getElementById("password");

  //   formData.append("uname", uname);
  //   formData.append("password", password);
  //   fetch("/api/inscription", {
  //     method: "POST",
  //     headers: myHeaders,
  //     // credentials: "same-origin",
  //     body: "test",
  //   }).then((response) => {
  //     if (response.ok) {
  //       response.json().then(function (data) {
  //         console.log("data", data);
  //       });
  //       // this.setState({ users: response.json() });
  //       // console.log("users", this.state.users);
  //     }
  //   });
  // }

  render() {
    return (
      <div>
        <form
          action="/api/inscription"
          method="post"
          enctype="application/x-www-form-urlencoded"
          id="signupForm"
        >
            <h1>Création d'un compte utilisateur</h1>
            <input
              type="text"
              placeholder="Entrez un nom d'utilisateur"
              name="uname"
              id="createuname"
              className="inputIndex"
              required
            />
            <input
              type="password"
              placeholder="Entrez un mot de passe"
              id="createpassword"
              name="password"
              className="inputIndex"
              onChange={this.validatePassword}
              required
            />
            <input
              type="password"
              placeholder="Confirmez le mot de passe"
              id="confirm_password"
              onKeyUp={this.validatePassword}
              className="inputIndex"
              required
            />

            <button type="submit">Confirm</button>
        </form>
      </div>
    );
  }
}

export default Inscrption;
