import { Component, OnInit, Input } from '@angular/core';
import { Reason } from '../shared/reason.service';

@Component({
  selector: 'app-reason-item',
  templateUrl: './reason-item.component.html',
  styleUrls: ['./reason-item.component.css']
})
export class ReasonItemComponent implements OnInit {

  @Input() reason!: Reason;
  
  constructor() { }

  ngOnInit(): void {
  }

}
