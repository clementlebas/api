import React from "react";
import ReactDOM from "react-dom";
import "../resources/static/main.css";
import CreateSurvey from "./surveyItem"

class Sondage extends React.Component {
  constructor(props) {
    super(props);
    this.state = { newSurvey: false };
    this.createSurvey = this.createSurvey.bind(this);
  };

  createSurvey() {
      const {newSurvey} = this.state;
      this.setState({newSurvey: !newSurvey});
  }


  render() {
    return (
      <div className="surveyWrapper" >
          <button className="surveyButton">Mes sondages</button>
          <button className="surveyButton" onClick={this.createSurvey}>Créer un sondage</button>
          {this.state.newSurvey ? <CreateSurvey /> : undefined}
      </div>
    );
  }
}

ReactDOM.render(<Sondage />, document.getElementById("survey"));
