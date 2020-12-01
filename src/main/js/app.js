import React from 'react';
import ReactDOM from 'react-dom';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
	    const myHeaders = new Headers({
              'Content-Type': 'application/json; charset=UTF-8',
               Accept: 'application/json, text/javascript, */*; q=0.01'
            });

	    const params = {
              method: 'GET',
              headers: myHeaders,
        };
		fetch('/api/users', params).then(response => {
		    console.log('response', response);
           if (response.ok) {
             this.setState({users: response.json()})
           };
        });
	}

	render() {
		return (
			<div>{this.state.users}</div>
		)
	}
}

ReactDOM.render(<App />, document.getElementById('react'))