import { Component, EventEmitter, Input, Output, SimpleChanges, ViewChild } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { MatCalendar, MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { AgendaService } from 'src/app/service/agenda.service';


@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css'],
  standalone: true,
  imports: [MatCardModule, MatDatepickerModule, MatNativeDateModule],
})
export class DatepickerComponent {

  selectedDate : Date = new Date();

  @Input() childData!: Date;
  @Output() dataChanged = new EventEmitter<Date>();  

  constructor(
    private agendaService : AgendaService
  ){}

  callAgendaService(arg: any){
    this.agendaService.changeDateInFullcalendar(arg);
  }

  onSelect(event: any){
    // emitindo evento e argumento para o componente pai (AgendaComponent)
    this.dataChanged.emit(event);
    this.selectedDate = event;
  } 
}
