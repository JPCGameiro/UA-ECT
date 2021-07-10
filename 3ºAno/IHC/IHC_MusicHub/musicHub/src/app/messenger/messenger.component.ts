import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmojiButton } from '@joeattardi/emoji-button';

@Component({
  selector: 'app-messenger',
  templateUrl: './messenger.component.html',
  styleUrls: ['./messenger.component.css']
})
export class MessengerComponent implements OnInit {
  toogleMX :boolean;
  indivMX

  toogleMXshow(){
    this.toogleMX = !this.toogleMX;
  }

  public router : Router

  ngOnInit(): void {
    this.toogleMX = true;
  }

  constructor() { 
    
  }

}
