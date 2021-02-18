import { Component, OnInit, Input } from '@angular/core';
import { Game } from '../shared/game.service';

@Component({
  selector: 'app-game-item',
  templateUrl: './game-item.component.html',
  styleUrls: ['./game-item.component.css']
})
export class GameItemComponent implements OnInit {

  @Input() game!: Game; 
  constructor() { }

  ngOnInit(): void {
  }

}
