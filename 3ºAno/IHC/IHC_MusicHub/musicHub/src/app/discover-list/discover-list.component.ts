import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-discover-list',
  templateUrl: './discover-list.component.html',
  styleUrls: ['./discover-list.component.css']
})
export class DiscoverListComponent implements OnInit {

  constructor(public router: Router) { }

  setSaving(element, text1, text2){
    if(element.disabled){
      element.textContent = text1;
      element.disabled = false;
    }else{
      element.disabled = true;
      element.textContent = text2;
    }
  }
  
  ngOnInit(): void {
  }

}
