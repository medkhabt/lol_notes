import { Component, OnInit } from '@angular/core';
import { Game, GameService } from '../shared/game.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  games: Game[]=[]; 
  constructor(private gameService: GameService) { }

  ngOnInit(): void {
    this.games = this.gameService.getGames(); 
  }

}
