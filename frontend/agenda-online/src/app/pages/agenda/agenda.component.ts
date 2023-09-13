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

  changeMonth(mode: 'prev' | 'next') {
    this.currentDate =
      mode === 'prev'
        ? addDays(this.currentDate, -30)
        : addDays(this.currentDate, 30)
  }

  nextMonthFirstDay(selectedDate : Date) {
    const activeDate = selectedDate;
    const month = activeDate.getMonth();
    const year = activeDate.getFullYear();
    const prevMonth = new Date(year, month - 1, 1);
    this.currentDate = prevMonth;
  }

  prevMonthFirstDay(selectedDate : Date) {
    const activeDate = selectedDate;
    const month = activeDate.getMonth();
    const year = activeDate.getFullYear();
    const prevMonth = new Date(year, month + 1, 1);
    this.currentDate = prevMonth;
  }
  
}
