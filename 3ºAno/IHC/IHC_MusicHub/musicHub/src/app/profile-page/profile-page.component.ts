import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
  // js modal code, convert to TS
  // Get the modal
  // const modal = document.getElementById("myModal");
  //
  // Get the button that opens the modal
  // const btn = document.getElementById("myBtn");
  //
  // Get the <span> element that closes the modal
  // const span = document.getElementsByClassName("close")[0];
  //
  // When the user clicks on the button, open the modal
  // btn.onclick = function() {
  //   modal.style.display = "block";
  // }
  //
  // When the user clicks on <span> (x), close the modal
  // span.onclick = function() {
  //   modal.style.display = "none";
  // }
  //
  // When the user clicks anywhere outside of the modal, close it
  // window.onclick = function(event) {
  //   if (event.target == modal) {
  //     modal.style.display = "none";
  //   }
  // }
  elementdisable = true
  bigMx : boolean = true;
  newPost() {
    let modal = document.getElementById('newPostModal');
    modal.style.display = 'block';
  }
  closeNewPost() {
    let modal = document.getElementById('newPostModal');
    modal.style.display = 'none';
  }
  post() {
    let modal = document.getElementById('newPostModal');
    modal.style.display = 'none';
    this._snackBar.open("Added with success!")
  }

  toogleBigMx() {
    this.bigMx = !this.bigMx;
  }
  constructor(
    public router: Router,
    private _snackBar: MatSnackBar ) { }

  ngOnInit(): void {}
}

// js modal code, convert to TS
// Get the modal
// const modal = document.getElementById("myModal");
//
// Get the button that opens the modal
// const btn = document.getElementById("myBtn");
//
// Get the <span> element that closes the modal
// const span = document.getElementsByClassName("close")[0];
//
// When the user clicks on the button, open the modal
// btn.onclick = function() {
//   modal.style.display = "block";
// }
//
// When the user clicks on <span> (x), close the modal
// span.onclick = function() {
//   modal.style.display = "none";
// }
//
// When the user clicks anywhere outside of the modal, close it
// window.onclick = function(event) {
//   if (event.target == modal) {
//     modal.style.display = "none";
//   }
// }
