import { Component, OnInit } from '@angular/core';
import { Post} from "../post"

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  elementdisable = true

  examplePost : Post = {
    username: "Pedro Abreu",
    smldescription: "Work of art!",
    description: "Vejam só a minha capa de albúm"
  }

  examplePost1 : Post = {
    username: "Green Day",
    smldescription: "Check our new single!",
    description: "The single Pollyanna is already available on all platforms. #stay green"
  }
  
  examplePost2 : Post = {
    username: "Ana Simões",
    smldescription: "What a blast!!",
    description: "Last night U2 concert was out of this world! propz to them!"
  }

  constructor() { }

  ngOnInit(): void {
  }

}
