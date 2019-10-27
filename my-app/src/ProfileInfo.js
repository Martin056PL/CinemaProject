import React, { Component } from 'react'

export class ProfileInfo extends Component {

    constructor(props) {
		super(props);
		this.state = {
			activeProfile: ''
		};
    }
    
    componentDidMount() {
        const URL = `http://localhost:8080/profile`;

        fetch(URL, {
			headers: {
				'Authorization': 'Basic ' + btoa('root:root')
		}})
		.then(response => response.text())
		.then(data => this.setState({
			activeProfile: data
		}))
		.catch(error => {
			console.log(error);
		})
    }

    render() {
        const activeProfile = this.state.activeProfile;
        return (
            <div>
                <h4>Active profile: {activeProfile}</h4>
            </div>
        )
    }
} export default ProfileInfo
