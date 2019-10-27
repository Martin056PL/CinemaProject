import React, { Component } from 'react';
import { Row, Col, Spinner, ButtonGroup, Button, Container, Table} from 'reactstrap';
import { Link } from 'react-router-dom';

class TaskList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isLoading: false,
      tasks: [],
    };
    this.remove = this.remove.bind(this);
  }


componentDidMount() {
  this.setState({isLoading: true})
  fetch(`http://localhost:8080/api/tasks`, {
    headers: {
      'Authorization': 'Basic ' + btoa('root:root')
    }
  }
)
  .then(response => response.json())
  .then(data => this.setState({tasks: data, isLoading: false
  }))   
}

async remove(id) {
    await fetch(`http://localhost:8080/api/tasks/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa('root:root')
      }
    }).then(() => {
      let updatedTasks = [...this.state.tasks].filter(i => i.id !== id);
      this.setState({tasks: updatedTasks});
    });
}

  render() {
    const {tasks, isLoading} = this.state;

    if (isLoading) {
			return (
				<Container style={{paddingTop: "40vh"}}>
					<Row className="justify-content-center">
						<Col xs="auto">
							<Spinner type="grow" color="primary" />
							<Spinner type="grow" color="secondary" />
							<Spinner type="grow" color="success" />
							<Spinner type="grow" color="danger" />
							<Spinner type="grow" color="warning" />
							<Spinner type="grow" color="info" />
							<Spinner type="grow" color="light" />
							<Spinner type="grow" color="dark" />
						</Col>
					</Row>
				</Container>
			)
    }
    

    let taskList = tasks.map(task => {
        return( 
        <tr key={task.id}>
          <td>{task.name}</td>
          <td>{task.description}</td>
          <td>{task.priority}</td>
          <td>{task.status}</td>
          <td>
            <ButtonGroup>
              <Button size="sm" color="primary" tag={Link} to={ "/tasks/"+ task.id}>Edit</Button>
              &nbsp;
              <Button size="sm" color="danger" onClick={() => this.remove(task.id)}>Delete</Button>
            </ButtonGroup>
          </td>
        </tr>
      )});

    return (
      <div>
        <Container fluid>
          <div className="float-right">
            <Button color="secondary" tag={Link} to={"/"}>Back to movie list</Button>
            &nbsp;&nbsp;&nbsp;
            <Button color="success" tag={Link} to={"/tasks/new"}>Add task</Button>
          </div>
          <h3>My tasks:</h3>
          <div>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Description</th>
              <th width="20%">Priority</th>
              <th width="20%">Status</th>
              <th width="20%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {taskList}
            </tbody>
          </Table>
          </div>
        </Container>
      </div>
    );
  }
}

export default TaskList;