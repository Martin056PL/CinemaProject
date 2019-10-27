import React, { Component } from 'react';
import './App.css';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import ProfileInfo from './ProfileInfo';

class Home extends Component {
  render() {
    return (
      <div>
        <ProfileInfo />
        <Container fluid>
          <Button color="link"><Link to="/movies">Find your movies!</Link></Button>
          <Button color="link"><Link to="/employees">Find employees!</Link></Button>
          <Button color="link"><Link to="/tasks">Find tasks!</Link></Button>
        </Container>
      </div>
    );
  }
}

export default Home;