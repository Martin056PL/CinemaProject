import React, { Component } from 'react';
import { DropdownToggle, DropdownMenu, DropdownItem, Dropdown, Row, Col, Spinner, ButtonGroup, Button, Container, Table, Input } from 'reactstrap';
import { Link } from 'react-router-dom';
import Pagination from 'react-js-pagination';

class MovieList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      movies: [],
      activePage:0,
      itemsCountPerPage:null,
      totalItemsCount:null,

      sortByDropdownOpen: false,
			sortOrderDropdownOpen: false,
			sortByLabel: 'Title',
			sortOrderLabel: 'Ascending',
			sortBy: 'title',
			sortOrder: 'asc',

      searchingTitle: '',
      searchingDatePremiere: '',


    };
    this.remove = this.remove.bind(this);
    this.handleTitleSearch = this.handleTitleSearch.bind(this);
    this.handleDatePremiereSearch = this.handleDatePremiereSearch.bind(this);
    this.fetchURL = this.fetchURL.bind(this)
    this.handlePageChange = this.handlePageChange.bind(this)
    this.handleReset = this.handleReset.bind(this);
		this.toggleSortByDropdown = this.toggleSortByDropdown.bind(this);
		this.toggleSortOrderDropdown = this.toggleSortOrderDropdown.bind(this);
 
  }

  fetchURL(page) {
    const RESOURCE = `http://localhost:8080/rest/movies`;
		const PAGE_PARAM = `page=${page}`;
		const SIZE_PARAM = `size=${this.state.itemsCountPerPage}`;
    const TITLE_PARAM = `title=${this.state.searchingTitle}`;
    const DATEPREMIERE_PARAM = `datePremiere=${this.state.searchingDatePremiere}`;
		const SORT_PARAM = `sort=${this.state.sortBy},${this.state.sortOrder}`;
		const PARAMS = PAGE_PARAM + "&" + SIZE_PARAM + "&" + TITLE_PARAM + "&" + DATEPREMIERE_PARAM + "&" + SORT_PARAM;
		const URL = RESOURCE + "?" + PARAMS;

    this.setState({isLoading: true})
    
			fetch(URL, { 
          headers: {
				  'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': 'Basic ' + btoa('root:root')
          }
        }
      )
				.then(response => response.json())
				.then(data => this.setState({
					movies: data.content,

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
    await fetch(`http://localhost:8080/rest/movies/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa('root:root')
      }
    }).then(() => {
      let updatedMovies = [...this.state.movies].filter(i => i.movieId !== id);
      this.setState({movies: updatedMovies});
    });
  }
  
  handleTitleSearch(e) {
		this.setState({ searchingTitle: e.target.value }, () => this.fetchURL(this.state.activePage));
  }
  
  handleDatePremiereSearch(e) {
		this.setState({ searchingDatePremiere: e.target.value }, () => this.fetchURL(this.state.activePage));
	}

  handlePageChange(pageNumber) {
		console.log(`active page is ${pageNumber}`);
		this.setState({activePage: pageNumber}, () => this.fetchURL(pageNumber-1, this.state.searchingTitle));
		this.fetchURL(pageNumber-1, this.state.searchingTitle);
  }

  handleReset(e) {
		this.setState({
      searchingTitle: '',
      searchingDatePremiere: '',
			sortBy: 'title',
			sortOrder: 'asc',
			sortByLabel: 'Sort',
			sortOrderLabel: 'Ascending'
		}, () => this.fetchURL(this.state.activePage));
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
    const {movies, isLoading} = this.state;

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

    const movieList = movies.map(movie => {
        return( 
        <tr key={movie.movieId}>
          <td style={{whiteSpace: 'nowrap'}}>{movie.title}</td>
          <td>{movie.type}</td>
          <td>{movie.datePremiere}</td>
          <td>
          <ButtonGroup>
          <Button size="sm" color="warning" tag={Link} to={"/movies/" + movie.movieId + "/actors"}>Actors</Button>
          </ButtonGroup>
          </td>
          <td>
              <Button size="sm" color="primary" tag={Link} to={"/movies/" + movie.movieId}>Edit</Button>
              &nbsp;
              <Button size="sm" color="danger" onClick={() => this.remove(movie.movieId)}>Delete</Button>
          </td> 
        </tr>  
      )});

    return (
      <div>
        <Container fluid>
          <div className="float-right">
            <Button color="secondary" tag={Link} to={"/"} >Back to main menu</Button>
          &nbsp;&nbsp;&nbsp;
            <Button color="success" tag={Link} to="/movies/new">New movie</Button>
          </div>
          <h3>My movies:</h3>
          <br></br>
          <Row>
						<Col md={5}><Input type="text" onChange={this.handleTitleSearch} placeholder="Search by title..." value={this.state.searchingTitle}/></Col>				
            <Col md={2}><Input className="input" type="date" onChange={this.handleDatePremiereSearch} placeholder="Search date..." value={this.state.searchingDatePremiere}/></Col>         
            <Col><Button onClick={this.handleReset}>Reset</Button></Col>
					</Row>
          <br></br>
          <Row>
					<Col md={1}>
					<Dropdown isOpen={this.state.sortByDropdownOpen} toggle={this.toggleSortByDropdown}>
						<DropdownToggle caret>
							Sort by: {this.state.sortByLabel + ' '}
						</DropdownToggle>
						<DropdownMenu>
							<DropdownItem onClick={() => { this.setState({ sortBy: 'title', sortByLabel: 'Title' }, () => this.fetchURL(this.state.activePage))}}>
								Title
							</DropdownItem>
							<DropdownItem onClick={() => { this.setState({ sortBy: 'datePremiere', sortByLabel: 'Date Premiere' }, () => this.fetchURL(this.state.activePage))}}>
								Date Premiere
							</DropdownItem>
              <DropdownItem onClick={() => { this.setState({ sortBy: 'type', sortByLabel: 'Type' }, () => this.fetchURL(this.state.activePage))}}>
								Type
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
              <th width="30%">Title</th>
              <th width="10%">Type</th>
              <th width="10%">Date Premiere</th>
              <th width="7%">Actors</th>
              <th width="10%">Movie Options</th>
            </tr>
            </thead>
            <tbody>
            {movieList}
            </tbody>
          </Table>
          <div className="float-left">
          <Pagination
              hideDisabled
							activePage={this.state.activePage}
							itemsCountPerPage={this.state.itemsCountPerPage}
							totalItemsCount={this.state.totalItemsCount}
							pageRangeDisplayed={3}
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

export default MovieList;