import { Component, EventEmitter, Output } from '@angular/core';
import { addDays } from '@fullcalendar/core/internal';

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

  nextDay() {
    this.currentDate = addDays(this.currentDate, 1)
  }

  prevDay() {
    this.currentDate = addDays(this.currentDate, -1)
  }
}
