import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ARTISTS } from '../mock-musicians';
import { PLACES } from '../mock-establishments';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-followers-list',
  templateUrl: './followers-list.component.html',
  styleUrls: ['./followers-list.component.css']
})
export class FollowersListComponent implements OnInit {

  artists = ARTISTS
  places = PLACES

  constructor(public router: Router, public route: ActivatedRoute) { 
    if(this.router.url.startsWith('/searchmusicians?')){
      route.queryParams.subscribe(
        params => {console.log('queryParams', params['genre'], params['location'], params['musician']);
        this.artists = [];
        if(params['genre'] != "" && params['location'] == "" && params['musician'] == ""){
          for( let a of ARTISTS){
            if (a.genre==params['genre']){
              this.artists.push(a)
            }
          }
        }
        else if(params['genre'] == "" && params['location'] != "" && params['musician'] == ""){
          for( let a of ARTISTS){
            if (a.location==params['location']){
              this.artists.push(a)
            }
          }
        }
        else if(params['genre'] != "" && params['location'] != "" && params['musician'] == ""){
          for( let a of ARTISTS){
            if (a.genre==params['genre'] && a.location==params['location']){
              this.artists.push(a)
            }
          }
        }
        else if(params['musician'] != ""){
          for( let a of ARTISTS){
            if (a.name==params['musician']){
              this.artists.push(a)
            }
          }
        }
      });
    }
    else if(this.router.url.startsWith('/searchestablishments?')){
      route.queryParams.subscribe(
        params => {console.log('queryParams', params['genre'], params['location'], params['establishment']);
        this.places = [];
        if(params['genre'] != "" && params['location'] == "" && params['establishment'] == ""){
          for( let p of PLACES){
            if (p.genre==params['genre']){
              this.places.push(p)
            }
          }
        }
        else if(params['genre'] == "" && params['location'] != "" && params['establishment'] == ""){
          for( let p of PLACES){
            if (p.location==params['location']){
              this.places.push(p)
            }
          }
        }
        else if(params['genre'] != "" && params['location'] != "" && params['establishment'] == ""){
          for( let p of PLACES){
            if (p.location==params['location'] && p.genre==params['genre']){
              this.places.push(p)
            }
          }
        }
        else if(params['establishment'] != ""){
          for( let p of PLACES){
            if (p.name==params['establishment']){
              this.places.push(p)
            }
          }
        }
        });
    }
  }

  ngOnInit(): void {
  }

}
