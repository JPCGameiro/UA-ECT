import { Component, OnInit } from '@angular/core';
import { Contact} from "../contact"


@Component({
  selector: 'app-messengercontact',
  templateUrl: './messengercontact.component.html',
  styleUrls: ['./messengercontact.component.css']
})
export class MessengercontactComponent implements OnInit {

  exampleContact : Contact = {
    username : "Foo Fighters",
    smldescription: "you: See you there then!  - 2d ago"
  }

  exampleContact1 : Contact = {
    username : "Green Day",
    smldescription: "them: great to see u!  - 5h ago"
  }

  exampleContact2 : Contact = {
    username : "Three Doors Down",
    smldescription: "them: Listen to our new song bro!"
  }

  exampleContact3 : Contact = {
    username : "Mariza",
    smldescription: "them: Nice!  - 4h ago"
  }

  exampleContact4 : Contact = {
    username : "Tim",
    smldescription: "them: Lá Estarei!  - 4h ago"
  }
  exampleContact5 : Contact = {
    username : "João Batista",
    smldescription: "them: Quinta Feita à noite  - 4h ago"
  }

  exampleContact6 : Contact = {
    username : "Ana Luís",
    smldescription: "them: Adeus até à próxima  - 4h ago"
  }

  exampleContact7 : Contact = {
    username : "Pearl Jam",
    smldescription: "them: Five Hours man  - 4h ago"
  }

  exampleContact8 : Contact = {
    username : "JazzCaffe Aveiro",
    smldescription: "them: see you then - 4h ago"
  }

  constructor() { }

  ngOnInit(): void {
  }

}