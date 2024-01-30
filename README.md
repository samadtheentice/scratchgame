# scratchgame

1. Software Used

	a. Java 1.8\
	b. IntelliJ IDEA community edition\
	c. Gradle 9.0

2. Libraries Used

	a. Junit\
	b. jackson-databind

3. Instructions To Build the project


	a. Pull the scratchgame project from repo - https://github.com/samadtheentice/scratchgame
	b. In IntelliJ or Eclipse, locate the project from filesystem and open the project
	c. From IntelliJ terminal windo
		Use ./gradlew clean to remove the generated jar
		Use ./gradlew build to build the project
	d. A sample config file is located in the project resource folder e.g, C:\scratchgame\src\main\resources\config.json
	e. To run the project, open command prompt, and change the directory to the location where the jar file is created e.g, C:\scratchgame\build\libs
	f. Use command -> java -jar scratchgame-1.0-SNAPSHOT.jar --config C:\scratchgame\src\main\resources\config.json --betting-amount 100


4. Program Running time - 490ms avg

5. Unit test cases - 13

6. Test Examples


	a. Input

	C:\scratchgame\build\libs>java -jar scratchgame-1.0-SNAPSHOT.jar --config C:\scratchgame\src\main\resources\config.json --betting-amount 110


	Output
	{
		"matrix": [
			[
				"F",
				"C",
				"5x"
			],
			[
				"B",
				"C",
				"E"
			],
			[
				"C",
				"E",
				"E"
			]
		],
		"reward": 14300,
		"applied_winning_combinations": {
			"C": [
				"same_symbol_3_times",
				"same_symbols_vertically"
			],
			"E": [
				"same_symbol_3_times",
				"same_symbols_vertically"
			]
		},
		"applied_bonus_symbol": "5x"
	}

	b. Input

	C:\scratchgame\build\libs>java -jar scratchgame-1.0-SNAPSHOT.jar --config C:\scratchgame\src\main\resources\config.json --betting-amount 150

	Output

	{
		"matrix": [
			[
				"F",
				"E",
				"+500"
			],
			[
				"E",
				"C",
				"D"
			],
			[
				"F",
				"D",
				"D"
			]
		],
		"reward": 2450,
		"applied_winning_combinations": {
			"D": [
				"same_symbol_3_times",
				"same_symbols_vertically"
			],
			"F": [
				"same_symbols_vertically"
			]
		},
		"applied_bonus_symbol": "+500"
	}



