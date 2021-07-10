import { Component, OnInit, HostListener } from '@angular/core';


@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  bigMx : boolean = true;

  toogleBigMx(){
    this.bigMx = !this.bigMx;
  }

  @HostListener('window:scroll', ['$event']) // for window scroll events
  onScroll(event) {
    const verticalOffset = window.pageYOffset
    if (verticalOffset > 430){
      this.bigMx = false
    }else{
      this.bigMx = true
    }
  }

scrollToTop() {
  (function smoothscroll() {
      var currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
      if (currentScroll > 0) {
          window.requestAnimationFrame(smoothscroll);
          window.scrollTo(0, currentScroll - (currentScroll / 8));
      }
  })();
}

  constructor() { }

  ngOnInit(): void {
  }

}
