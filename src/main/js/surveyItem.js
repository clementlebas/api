import React from "react";
import ReactDOM from "react-dom";
import "../resources/static/main.css";

class CreateSurvey extends React.Component {
  constructor(props) {
    super(props);
        this.state = { surverForm: [] };
        this.generateForm = this.generateForm.bind(this);
  }

    generateForm() {
      const {surverForm} = this.state;
      const nameQuestion = document.getElementById("nameQuestion").value;
      const numberQuestion = document.getElementById("numberQuestion").value;

      let i = Number(numberQuestion);
     console.log("numberQuestion", typeof numberQuestion);
      while(i > 0) {
      console.log("i", i);
        i--;
        this.setState({surverForm: surverForm.push("<div>Question</div>")});
      }
        console.log("surverForm", surverForm);
    }

  render() {
  const {surverForm} = this.state;

    return (
      <div className="surveyItemWrapper" >
        <input type="text" placeholder="Entrez un nom de sondage" id="nameQuestion" required />
        <input type="number" placeholder="Combien de questions ?" id="numberQuestion" required />
        <button onClick={this.generateForm}>Valider</button>
        <div>{
        surverForm && surverForm.map(item => (
                          <li key={item}>{item}</li>
                        ))}
        </div>
      </div>
    );
  }
}

export default CreateSurvey;
