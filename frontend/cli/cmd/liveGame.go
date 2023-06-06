/*
Copyright © 2023 NAME HERE <EMAIL ADDRESS>
*/
package cmd

import (
	services "com.medkha/lono/services"
	"github.com/spf13/cobra"
)

// liveGameCmd represents the liveGame command
var liveGameCmd = &cobra.Command{
	Use:   "liveGame",
	Short: "Show your deaths while it happend in your live game.",
	Long: `For now it only shows the death event when it occurs , but 
	it should do more, like choosing a reason of death and giving more info about the death`,
	Run: func(cmd *cobra.Command, args []string) {
		services.GetEventsFromLiveGame()
	},
}

func init() {
	rootCmd.AddCommand(liveGameCmd)

	// Here you will define your flags and configuration settings.

	// Cobra supports Persistent Flags which will work for this command
	// and all subcommands, e.g.:
	// liveGameCmd.PersistentFlags().String("foo", "", "A help for foo")

	// Cobra supports local flags which will only run when this command
	// is called directly, e.g.:
	// liveGameCmd.Flags().BoolP("toggle", "t", false, "Help message for toggle")
}
