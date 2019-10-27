import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import MovieList from './MovieList';
import MovieEdit from './MovieEdit';
import ActorList from './ActorList';
import ActorEdit from './ActorEdit';
import EmployeeList from './otherComponents/EmployeeList';
import TaskList from './otherComponents/TaskList';
import TaskEdit from './otherComponents/TaskEdit';

class App extends Component {

  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/movies' exact={true} component={MovieList}/>
          <Route path='/employees' exact={true} component={EmployeeList}/>
          <Route path='/tasks' exact={true} component={TaskList}/>
          <Route path='/tasks/:id' exact={true} component={TaskEdit}/>
          <Route path='/movies/:id' exact={true} component={MovieEdit}/>
          <Route path='/movies/:id/actors' exact={true} component={ActorList}/>
          <Route path='/movies/:movieId/actors/:id' component={ActorEdit}/>
        
        </Switch>
      </Router>
    )
  }
}

export default App;
