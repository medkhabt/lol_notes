import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GameService, Game } from '../shared/game.service';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit {

  game!: Game; 
  constructor(private route: ActivatedRoute,
              private gameService: GameService) { }

  ngOnInit(): void {
   
    const gameId: number = parseInt(
                              this.route.snapshot.params['gameId']
    ); 
    console.log("gameId : " + gameId);
    this.game = this.gameService.getGameById(gameId); 
  }

}
