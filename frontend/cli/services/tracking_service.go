package services

import (
	"fmt"
	"io"
	"log"
	"net/http"

	net "github.com/subchord/go-sse"
)

func GetEventsFromLiveGame() {
	feed, err := net.ConnectWithSSEFeed("http://localhost:8999/games/live-game", nil)
	if err != nil {
		log.Fatal(err)
		return
	}

	sub, err := feed.Subscribe("")
	if err != nil {
		return
	}

	for {
		select {
		case evt := <-sub.Feed():
			log.Print(evt)
		case err := <-sub.ErrFeed():
			log.Fatal(err)
			return
		}
	}

	sub.Close()
	feed.Close()

}
func GetAllReasons() {
	res, errGet := http.Get("http://localhost:8999/reasons")
	if errGet != nil {
		log.Fatal(errGet)
	}
	body, err := io.ReadAll(res.Body)
	res.Body.Close()

	if res.StatusCode > 299 {
		log.Fatalf("Response failed with status code: %d and\nbody: %s\n", res.StatusCode, body)
	}

	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("%s", body)
}
