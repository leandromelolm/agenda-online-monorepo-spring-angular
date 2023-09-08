import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.css']
})
export class AgendaComponent {
  
  currentDate = new Date();

  handleDataChange(newData: Date) {
    this.currentDate = newData;
  }
}
