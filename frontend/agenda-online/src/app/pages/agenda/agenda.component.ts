import { Component, EventEmitter, Output } from '@angular/core';
import { addDays } from '@fullcalendar/core/internal';

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.css']
})
export class AgendaComponent {
  
  selectedDate = new Date();

  handleDataChange(newData: Date) {
    this.selectedDate = newData;
  }

  nextDay() {
    this.selectedDate = addDays(this.selectedDate, 1)
  }

  prevDay() {
    this.selectedDate = addDays(this.selectedDate, -1)
  }

  changeMonth(mode: 'prev' | 'next') {
    this.selectedDate =
      mode === 'prev'
        ? addDays(this.selectedDate, -30)
        : addDays(this.selectedDate, 30)
  }

  nextMonthFirstDay(selectedDate : Date) {
    const activeDate = selectedDate;
    const month = activeDate.getMonth();
    const year = activeDate.getFullYear();
    const prevMonth = new Date(year, month - 1, 1);
    this.selectedDate = prevMonth;
  }

  prevMonthFirstDay(selectedDate : Date) {
    const activeDate = selectedDate;
    const month = activeDate.getMonth();
    const year = activeDate.getFullYear();
    const prevMonth = new Date(year, month + 1, 1);
    this.selectedDate = prevMonth;
  }
  
}
