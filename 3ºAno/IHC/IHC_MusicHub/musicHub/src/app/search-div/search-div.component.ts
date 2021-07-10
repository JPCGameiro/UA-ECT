import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-search-div',
  templateUrl: './search-div.component.html',
  styleUrls: ['./search-div.component.css']
})
export class SearchDivComponent implements OnInit {

  constructor(
    public router: Router,
    private snackBar: MatSnackBar) { }
  ngOnInit(): void { }

  showSnack(){
    this.snackBar.open("Search Completed")
  }

  searchForm = new FormGroup({
    establishment: new FormControl(''),
    musician: new FormControl(''),
    genre: new FormControl(''),
    location: new FormControl(''),
  });

  onSubmit() {
    if(this.router.url == '/musicians' || this.router.url.startsWith('/searchmusicians?')){
      console.warn(this.searchForm.value);
      if(this.searchForm.value.genre == "" && this.searchForm.value.musician == "" && this.searchForm.value.location == ""){
        alert("You must especify at least one search filter");
      }
      else{
        this.router.navigate(['/searchmusicians'], { queryParams: { 
          genre: this.searchForm.value.genre, 
          musician: this.searchForm.value.musician,
          location: this.searchForm.value.location }});   
      }
    }
    else if((this.router.url == '/establishments' || this.router.url.startsWith('/searchestablishments?'))){
      console.warn(this.searchForm.value);
      if(this.searchForm.value.genre == "" && this.searchForm.value.establishment == "" && this.searchForm.value.location == ""){
        alert("You must especify at least one search filter");
      }
      else{
        this.router.navigate(['/searchestablishments'], { queryParams: { 
          genre: this.searchForm.value.genre, 
          establishment: this.searchForm.value.establishment,
          location: this.searchForm.value.location }});   
      }
    }
  }

}
