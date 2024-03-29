# scratchgame

1. Software Used

	a. Java 1.8\
	b. IntelliJ IDEA community edition\
	c. Gradle 9.0

2. Libraries Used

	a. Junit\
	b. jackson-databind

3. Instructions To Build the project


	a. Pull the scratchgame project from repo - https://github.com/samadtheentice/scratchgame \
	b. In IntelliJ or Eclipse, locate the project from filesystem and open the project \
	c. From IntelliJ terminal window \
		Use ./gradlew clean to remove the generated jar \
		Use ./gradlew build to build the project \
	d. A sample config file is located in the project resource folder e.g, C:\scratchgame\src\main\resources\config.json \
	e. To run the project, open command prompt, and change the directory to the location where the jar file is created e.g, C:\scratchgame\build\libs \
	f. Use command -> java -jar scratchgame-1.0-SNAPSHOT.jar --config C:\scratchgame\src\main\resources\config.json --betting-amount 100 


4. Program Running time - 490ms avg

5. Unit test cases - 13

6. Implemented all winning combinations including those specified as optional

7. The Solution should work for any NXN matrix, provided a valid configuration file is used, You can find example config files under resource folder, 		config.json - 3X3 matrix, config4x4.json - 4X4 matrix, config5x5.json -5X5 matrix 

8. Test Examples


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

	c. Input 4X4 Matrix
	
	java -jar scratchgame-1.0-SNAPSHOT.jar --config C:\scratchgame\src\main\resources\config4x4.json --betting-amount 100
	
	Output
	
	{
		"matrix": [
			[
				"F",
				"E",
				"F",
				"D"
			],
			[
				"F",
				"D",
				"E",
				"F"
			],
			[
				"F",
				"E",
				"D",
				"E"
			],
			[
				"10x",
				"E",
				"E",
				"C"
			]
		],
		"reward": 20000,
		"applied_winning_combinations": {
			"D": [
				"same_symbol_3_times"
			],
			"E": [
				"same_symbol_6_times"
			],
			"F": [
				"same_symbol_5_times",
				"same_symbols_vertically"
			]
		},
		"applied_bonus_symbol": "10x"
	}
	
	d. Input 5X5 Matrix
	
	java -jar scratchgame-1.0-SNAPSHOT.jar --config C:\scratchgame\src\main\resources\config5x5.json --betting-amount 100
	
	Output
	
	{
		"matrix": [
			[
				"E",
				"F",
				"C",
				"F",
				"D"
			],
			[
				"B",
				"A",
				"E",
				"E",
				"F"
			],
			[
				"E",
				"B",
				"E",
				"F",
				"E"
			],
			[
				"D",
				"E",
				"C",
				"F",
				"F"
			],
			[
				"F",
				"5x",
				"D",
				"D",
				"D"
			]
		],
		"reward": 16250,
		"applied_winning_combinations": {
			"D": [
				"same_symbol_5_times"
			],
			"E": [
				"same_symbol_7_times"
			],
			"F": [
				"same_symbol_7_times"
			]
		},
		"applied_bonus_symbol": "5x"
	}




