import React, { Component } from 'react';
import { DropdownToggle, DropdownMenu, DropdownItem, Dropdown, Row, Col, Spinner, ButtonGroup, Button, Container, Table, Input } from 'reactstrap';
import { Link } from 'react-router-dom';
import Pagination from 'react-js-pagination';

class ActorList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      actors: [],

      activePage:0,
      totalPages: '',
      itemsCountPerPage:'',
      totalItemsCount:'',

      sortByDropdownOpen: false,
			sortOrderDropdownOpen: false,
			sortByLabel: 'Last Name',
			sortOrderLabel: 'Ascending',
			sortBy: 'lastName',
      sortOrder: 'asc',
      
      searchingName: '',
      searchingAge: ''
    };
    this.remove = this.remove.bind(this);
    this.handleNameSearch = this.handleNameSearch.bind(this);
    this.handleAgeSearch = this.handleAgeSearch.bind(this);
    this.fetchURL = this.fetchURL.bind(this);
    this.handlePageChange = this.handlePageChange.bind(this);
    this.handleReset = this.handleReset.bind(this);
		this.toggleSortByDropdown = this.toggleSortByDropdown.bind(this);
		this.toggleSortOrderDropdown = this.toggleSortOrderDropdown.bind(this);
  }
 
  
fetchURL(page) {
  const RESOURCE = `http://localhost:8080/rest/movies/${this.props.match.params.id}/actors`;
  const PAGE_PARAM = `page=${page}`;
  const SIZE_PARAM = `size=${this.state.itemsCountPerPage}`;
  const NAME_PARAM = `name=${this.state.searchingName}`;
  const AGE_PARAM = `age=${this.state.searchingAge}`;
  const SORT_PARAM = `sort=${this.state.sortBy},${this.state.sortOrder}`;
  const PARAMS = PAGE_PARAM + "&" + SIZE_PARAM + "&" + NAME_PARAM + "&" + AGE_PARAM +  "&" + SORT_PARAM;
  const URL = RESOURCE + "?" + PARAMS;
  console.log(URL)
		fetch(URL, { 
      headers: {
      'Authorization': 'Basic ' + btoa('root:root')
      }
    })
				.then(response => response.json())
				.then(data => this.setState({
					actors: data.content,

					totalPages: data.totalPages,
					itemsCountPerPage: data.size,
					totalItemsCount: data.totalElements
				}))
    this.setState({isLoading: false})
  }

componentDidMount() {
    this.setState({isLoading: true});
    this.fetchURL(this.state.activePage)
}

async remove(id) {
    await fetch(`http://localhost:8080/rest/movies/${this.props.match.params.id}/actors/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa('root:root')
      }
    }).then(() => {
      let updatedActors = [...this.state.actors].filter(i => i.actorId !== id);
      this.setState({actors: updatedActors});
    });
  }

handleNameSearch(e) {
  this.setState({ searchingName: e.target.value }, () => this.fetchURL(this.state.activePage));
}

handleAgeSearch(e) {
  this.setState({ searchingAge: e.target.value }, () => this.fetchURL(this.state.activePage));
}

handleReset(e) {
  this.setState({
    searchingTitle: '',
    searchingYear: '',
    searchingGenre: '',

    sortBy: 'name',
    sortOrder: 'asc',
    sortByLabel: 'firstName',
    sortOrderLabel: 'Ascending'
  }, () => {
    this.fetchURL(this.state.activePage);
  });
}

 handlePageChange(pageNumber) {
		console.log(`active page is ${pageNumber}`);
		this.setState({activePage: pageNumber}, () => this.fetchURL(pageNumber-1, this.state.searchingName));
		this.fetchURL(pageNumber-1, this.state.searchingName);
  }

toggleSortByDropdown() {
  this.setState({
    sortByDropdownOpen: !this.state.sortByDropdownOpen
  });
}

toggleSortOrderDropdown() {
  this.setState({
    sortOrderDropdownOpen: !this.state.sortOrderDropdownOpen
  });
}

  render() {
    const {actors, isLoading} = this.state;

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
    

    let actorList = actors.map(actor => {
        return( 
        <tr key={actor.actorId}>
          <td style={{whiteSpace: 'nowrap'}}>{actor.firstName}</td>
          <td>{actor.lastName}</td>
          <td>{actor.age}</td>
          <td>
            <ButtonGroup>
              <Button size="sm" color="primary" tag={Link} to={ "/movies/" + this.props.match.params.id + "/actors/" + actor.actorId}>Edit</Button>
              &nbsp;
              <Button size="sm" color="danger" onClick={() => this.remove(actor.actorId)}>Delete</Button>
            </ButtonGroup>
          </td>
        </tr>
      )});

    return (
      <div>
        <Container fluid>
          <div className="float-right">
            <Button color="secondary" tag={Link} to={"/movies"}>Back to movie list</Button>
            &nbsp;&nbsp;&nbsp;
            <Button color="success" tag={Link} to={"/movies/" + this.props.match.params.id + "/actors/new"}>Add actor</Button>
          </div>
          <h3>My actors:</h3>
          <br></br>
          <Row>
						<Col md={5}><Input className="input" type="text" onChange={this.handleNameSearch} placeholder="Search Actor's Name..." value={this.state.searchingTitle}/></Col>
						<Col md={2}><Input className="input" type="number" onChange={this.handleAgeSearch} placeholder="Search Actors's Age..." value={this.state.searchingAge}/></Col>
						<Col><Button onClick={this.handleReset}>Reset</Button></Col>
					</Row>
          <br></br>
          <Row>
					<Col md={2}>
					<Dropdown isOpen={this.state.sortByDropdownOpen} toggle={this.toggleSortByDropdown}>
						<DropdownToggle caret>
							Sort by: {this.state.sortByLabel + ' '}
						</DropdownToggle>
						<DropdownMenu>
							<DropdownItem onClick={() => { this.setState({ sortBy: 'firstName', sortByLabel: 'First Name' }, () => this.fetchURL(this.state.activePage))}}>
								First Name
							</DropdownItem>
              <DropdownItem onClick={() => { this.setState({ sortBy: 'lastName', sortByLabel: 'Last Name' }, () => this.fetchURL(this.state.activePage))}}>
								Last Name
							</DropdownItem>
							<DropdownItem onClick={() => { this.setState({ sortBy: 'age', sortByLabel: 'Age' }, () => this.fetchURL(this.state.activePage))}}>
								Age
							</DropdownItem>
						</DropdownMenu>
					</Dropdown>
					</Col>

					<Col md={2}>
					<Dropdown isOpen={this.state.sortOrderDropdownOpen} toggle={this.toggleSortOrderDropdown}>
						<DropdownToggle caret>
							Sort order: {this.state.sortOrderLabel + ' '}
						</DropdownToggle>
						<DropdownMenu>
							<DropdownItem onClick={() => { this.setState({ sortOrder: 'asc', sortOrderLabel: 'Ascending' }, () => this.fetchURL(this.state.activePage))}}>
								Ascending (A -> Z)
							</DropdownItem>
							<DropdownItem onClick={() => { this.setState({ sortOrder: 'desc', sortOrderLabel: 'Descending' }, () => this.fetchURL(this.state.activePage))}}>
								Descending (Z -> A)
							</DropdownItem>
						</DropdownMenu>
					</Dropdown>
					</Col>
					</Row>
          
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">First Name</th>
              <th width="20%">Last Name</th>
              <th width="20%">Age</th>
              <th width="10%">Actor Options</th>
            </tr>
            </thead>
            <tbody>
            {actorList}
            </tbody>
          </Table>
          <div className="float-left">
          <Pagination
              hideDisabled
              activePage={this.state.activePage}
							itemsCountPerPage={this.state.itemsCountPerPage}
							totalItemsCount={this.state.totalItemsCount}
              pageRangeDisplayed={3}
              activeClass='page-active-item'
							itemClass='page-item'
							linkClass='btn btn-light'
							onChange={this.handlePageChange}
							firstPageText="First page"
							lastPageText="Last page"
							prevPageText="Previous page"
							nextPageText="Next page"
						/>
            </div>
        </Container>
      </div>
    );
  }
}

export default ActorList;