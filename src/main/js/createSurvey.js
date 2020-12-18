import React from "react";
import ReactDOM from "react-dom";
import "../resources/static/main.css";

class CreateSurvey extends React.Component {
  constructor(props) {
    super(props);
        this.state = { surverForm: {}, questionCount: 0, inputs: ['question-1'] };
  }

    /*generateForm() {
      const {surverForm, questionCount} = this.state;

      //const nameQuestion = document.getElementById("nameQuestion").value;
      //const numberQuestion = document.getElementById("numberQuestion").value;

      this.setState({questionCount: questionCount + 1});

      return <div>Question</div>;
    }*/

    addQuestion() {
        var newInput = `question-${this.state.inputs.length + 1}`;
        this.setState(prevState => ({ inputs: prevState.inputs.concat([newInput]) }));
    }

    removeQuestion() {
            this.state.inputs.pop();
            this.setState({ inputs: this.state.inputs });
        }

    validSurvey() {
        const {inputs} = this.state;
        const questions = {};

        const surveyName = document.getElementById("nameSurvey").value;
        const dateSurvey = document.getElementById("dateSurvey").value;

        for (const key of inputs) {
            console.log("key", key);

            questions[key] = document.getElementById(key).value;
         }

         const surveyJSON = {
            survey :{
                name: surveyName,
                date: dateSurvey,
                questions: questions
            }
         }

         console.log("questions", questions);
         console.log("surveyJSON", surveyJSON);

         const token = sessionStorage.getItem('token');
         console.log("token", token)

         const myHeaders = new Headers({
               "Content-Type": "application/json",
               Accept: "application/json",
               "Access-Control-Allow-Headers": "*",
               authorization: token,
             });

             fetch("/api/survey/create/", {
               method: "POST",
               headers: myHeaders,
               credentials: "same-origin",
               mode: "same-origin",
               body: JSON.stringify(surveyJSON),
             }).then((response) => {
               if (response.ok) {
                document.location.reload();
               }
             });
    }

  render() {
  const {inputs} = this.state;

  console.log("inputs", inputs)



    return (
      <div className="surveyItemWrapper" >
        <input type="text" placeholder="Entrez un nom de sondage" id="nameSurvey" required />
        <input type="date" placeholder="Entrez une date" id="dateSurvey" required />
        <div>
            <form>
               <div id="dynamicInput">
                   {inputs.map(input => <div>{input}<input id={input} style={{width: '100%'}} key={input} /></div>)}
               </div>
           </form>
           <button className="surveyButton" onClick={ () => this.addQuestion() }>
               AJOUTER QUESTION
           </button>
           <button className="surveyButton" onClick={ () => this.removeQuestion() }>
               EFFACER QUESTION
           </button>
           <button className="surveyButton" style={{marginLeft: '120px'}}onClick={ () => this.validSurvey() }>
             Valider sondage
          </button>
        </div>
      </div>
    );
  }
}

export default CreateSurvey;
