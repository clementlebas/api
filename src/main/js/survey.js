import React from "react";
import ReactDOM from "react-dom";
import "../resources/static/main.css";
import CreateSurvey from "./createSurvey";
import { JsonToTable } from "react-json-to-table";

class Survey extends React.Component {
  constructor(props) {
    super(props);
    this.state = { newSurvey: false, getSurvey: false, userSurvey: {} };
    this.createSurvey = this.createSurvey.bind(this);
    this.getSurvey = this.getSurvey.bind(this);
  }

  createSurvey() {
    const { newSurvey } = this.state;
    this.setState({ newSurvey: !newSurvey });
  }

  getSurvey() {
    const { userSurvey } = this.state;
    const token = sessionStorage.getItem("token");

    const myHeaders = new Headers({
      "Content-Type": "application/json",
      Accept: "application/json",
      "Access-Control-Allow-Headers": "*",
      authorization: token,
    });

    fetch("/api/survey/mysurvey", {
      method: "GET",
      headers: myHeaders,
      credentials: "same-origin",
      mode: "same-origin",
    }).then((response) => {
      if (response.ok) {
        response.json().then((responseData) => {
          if (responseData.survey) {
            this.setState((prevState) => ({ getSurvey: !prevState.getSurvey }));
            this.setState({ userSurvey: responseData.survey }, () => {
              console.log("userSurvey", userSurvey);
            });
          } else {
            this.setState({ getSurvey: false });
          }
        });
      }
    });
  }

  disconnect() {


    const myHeaders = new Headers({
      "Content-Type": "text/html; charset=utf-8",
    });

    fetch("/api/disconnect", {
      method: "GET",
      headers: myHeaders,
      credentials: "same-origin",
      mode: "same-origin",
    }).then(() => {
      window.location.href = "http://localhost:4000/api";
      sessionStorage.removeItem("token");
    });
  }

  render() {
    console.log("this.state.userSurvey", this.state.userSurvey);

    return (
      <div className="surveyWrapper">
        <button className="surveyButton" onClick={this.getSurvey}>
          Mes sondages
        </button>
        <button className="surveyButton" onClick={this.createSurvey}>
          Créer un sondage
        </button>
        {this.state.newSurvey ? <CreateSurvey /> : undefined}
        <div id="dynamicInput">
          {this.state.userSurvey && (
            <JsonToTable json={this.state.userSurvey} />
          )}
        </div>
        <button
          onClick={this.disconnect}
          style={{
            backgroundColor: "rgba(255, 49, 74, 0.51)",
            margin: "20px",
            marginLeft: "120px",
          }}
          className="surveyButton"
        >
          Se déconnecter
        </button>
      </div>
    );
  }
}

ReactDOM.render(<Survey />, document.getElementById("survey"));
