import React, { Component } from 'react';
import { Button, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';


class EmployeeList extends Component {

    constructor(props) {
        super(props);
        this.state = {
        isLoading: true,
        employees: [],
        };
      }

      componentDidMount() {
        
        fetch('http://localhost:8080/api/employees', { 
          headers: {
          'Authorization': 'Basic ' + btoa('root:root')
          }
        })
            .then(response => response.json())
            .then(data => this.setState({employees: data, isLoading: false}));
      }

      render() {
        const {employees, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
          }

      const employeeList = employees.map(employee => {
        return( 
        <tr key={employee.id}>
          <td>{employee.name}</td>
          <td>{employee.lastName}</td>
          <td>{employee.email}</td>
          <td>{employee.sex}</td>
          <td>{employee.createDate}</td>
          <td>{employee.positionId}</td>
        </tr>  
      )});

      return (
        <div>
          <Container fluid>
            <div className="float-right">
              <Button color="secondary" tag={Link} to={"/"} >Back to main menu</Button>
            </div>
            <h3>Employees:</h3>
            <Table className="mt-4">
              <thead>
              <tr>
                <th width="10%">name</th>
                <th width="10%">lastName</th>
                <th width="20%">email</th>
                <th width="10%">sex</th>
                <th width="10%">createDate</th>
                <th width="10%">positionId</th>
              </tr>
              </thead>
              <tbody>
              {employeeList}
              </tbody>
            </Table>
          </Container>
        </div>
      );
    }

}
export default EmployeeList;