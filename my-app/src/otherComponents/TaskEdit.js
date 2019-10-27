import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';


 class TaskEdit extends Component {
    emptyTask = {
        id:'',
        name: '',
        description: '',
        priority: '',
        status: ''
      };
    
      constructor(props) {
        super(props);
        this.state = {
          task: this.emptyTask
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }
    
      async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
          const task = await (await fetch(`http://localhost:8080/api/tasks/${this.props.match.params.id}`, {
            headers: {
              'Authorization': 'Basic ' + btoa('root:root')
            }
          })).json();
          this.setState({task: task});
        }
      }
    
      handleChange(event) {
        const target = event.target;
        const value = target.value;
        const id = target.id;
        let task = {...this.state.task};
        task[id] = value;
        this.setState({task});
      }
    
      async handleSubmit(event) {
        event.preventDefault();
        const {task} = this.state;
    
        if(task.id) {
            await fetch(`http://localhost:8080/api/tasks/${task.id}`, {
                method: 'PUT',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Basic ' + btoa('root:root')
                },
                body: JSON.stringify(task),
            });
        } else {
            await fetch('http://localhost:8080/api/tasks', {
                method: 'POST',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Basic ' + btoa('root:root')
                },
                body: JSON.stringify(task),
              });
        }
        
        this.props.history.push('/tasks');
      }
    
      render() {
        const {task} = this.state;
        const title = <h2>{task.id ? 'Edit Task' : 'Add Task'}</h2>;
    
        return <div>
          <Container>
            {title}
            <Form onSubmit={this.handleSubmit}>
              <FormGroup>
                <Label for="title">Name</Label>
                <Input type="text" name="name" placeholder="Task name MUST be uniqe" id="name" value={task.name || ''}
                       onChange={this.handleChange} autoComplete="name"/>
              </FormGroup>
              <FormGroup>
                <Label for="description">Description</Label>
                <Input type="text" name="description" placeholder="desc" id="description" value={task.description|| ''}
                       onChange={this.handleChange} autoComplete="description"/>
              </FormGroup>
              <FormGroup>
                <Label for="status">Status</Label>
                <Input type="number" name="status" placeholder=" 0 or 1" id="status" value={task.status || ''}
                       onChange={this.handleChange} autoComplete="status"/>
              </FormGroup>
              <FormGroup>
                <Label for="priority">Priority</Label>
                <Input type="text" name="priority" placeholder="SMALL or MEDIUM or IMPORTANT" id="priority" value={task.priority || ''}
                       onChange={this.handleChange} autoComplete="priority"/>
              </FormGroup>
              <FormGroup>
                <Button color="primary" type="submit">Save</Button>{' '}
                <Button color="secondary" tag={Link} to="/tasks">Cancel</Button>
              </FormGroup>
            </Form>
          </Container>
        </div>
      }
}
export default withRouter(TaskEdit);
