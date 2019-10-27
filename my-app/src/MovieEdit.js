import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';

 class MovieEdit extends Component {
    emptyMovie = {
        movieId:'',
        title: '',
        datePremiere: '',
        type: ''
      };
    
      constructor(props) {
        super(props);
        this.state = {
          movie: this.emptyMovie
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }
    
      async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
          const movie = await (await fetch(`http://localhost:8080/rest/movies/${this.props.match.params.id}`, {
            headers: {
              'Authorization': 'Basic ' + btoa('root:root')
            }
          })).json();
          this.setState({movie: movie});
        }
      }
    
      handleChange(event) {
        const target = event.target;
        const value = target.value;
        const movieId = target.id;
        let movie = {...this.state.movie};
        movie[movieId] = value;
        this.setState({movie});
      }
    
      async handleSubmit(event) {
        event.preventDefault();
        const {movie} = this.state;
    
        if(movie.movieId) {
            await fetch(`http://localhost:8080/rest/movies/${movie.movieId}`, {
                method: 'PUT',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Basic ' + btoa('root:root')
                },
                body: JSON.stringify(movie),
            });
        } else {
            await fetch('http://localhost:8080/rest/movies', {
                method: 'POST',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'Basic ' + btoa('root:root')
                },
                body: JSON.stringify(movie),
              });
        }
        
        this.props.history.push('/movies');
      }
    
      render() {
        const {movie} = this.state;
        const title = <h2>{movie.movieId ? 'Edit Movie' : 'Add Movie'}</h2>;
    
        return <div>
          <Container>
            {title}
            <Form onSubmit={this.handleSubmit}>
              <FormGroup>
                <Label for="title">Title</Label>
                <Input type="text" name="title" id="title" value={movie.title || ''}
                       onChange={this.handleChange} autoComplete="title"/>
              </FormGroup>
              <FormGroup>
                <Label for="datePremiere">Date Premiere</Label>
                <Input type="date" name="datePremiere" id="datePremiere" value={movie.datePremiere || ''}
                       onChange={this.handleChange} autoComplete="datePremiere"/>
              </FormGroup>
              <FormGroup>
                <Label for="type">Type</Label>
                <Input type="text" name="type" id="type" value={movie.type || ''}
                       onChange={this.handleChange} autoComplete="type"/>
              </FormGroup>
              <FormGroup>
                <Button color="primary" type="submit">Save</Button>{' '}
                <Button color="secondary" tag={Link} to="/movies">Cancel</Button>
              </FormGroup>
            </Form>
          </Container>
        </div>
      }
}
export default withRouter(MovieEdit);
