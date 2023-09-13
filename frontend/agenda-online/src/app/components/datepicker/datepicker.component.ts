import { Component, EventEmitter, Input, Output, SimpleChanges, ViewChild } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { MatCalendar, MatDatepicker, MatDatepickerModule } from '@angular/material/datepicker';
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

  // Decorador @Input para receber evento do componente pai
  @Input() dataFromParentToChild!: Date;

  // @Output para emitir evento do componente filho para o pai
  @Output() childEvent = new EventEmitter<Date>();

  @ViewChild('mat_calendar') matCalendar?: MatCalendar<Date>;

  constructor(
    private agendaService : AgendaService
  ){}

  callAgendaService(arg: any){
    this.agendaService.changeDateInFullcalendar(arg);
  }

  onSelect(event: any){
    // emitindo evento e argumento para o componente pai (AgendaComponent)
    this.childEvent.emit(event);
    this.selectedDate = event;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['dataFromParentToChild']) {
      this.handleChange();
    }
  }

  handleChange() {
    this.callSharedService(this.dataFromParentToChild);
    this.dateSelectedInDatePicker(this.dataFromParentToChild);
  }

  callSharedService(arg : any){
    this.agendaService.changeDateInFullcalendar(arg);
  }
  
  dateSelectedInDatePicker( arg: Date){
    if (!this.matCalendar) {
      console.error("'mat-calendar' ainda não está inicializado.");
      return;
    }
    const selectedDt = new Date(arg);
    this.selectedDate = selectedDt;
    this.matCalendar!.activeDate = selectedDt;
    this.matCalendar!.updateTodaysDate();
  }

}
