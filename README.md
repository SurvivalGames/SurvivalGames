![SurvivalGames](https://raw.githubusercontent.com/SurvivalGamesDevTeam/TheSurvivalGames/gh-pages/wiki/image/SurvivalGames.png)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[![Features](https://raw.githubusercontent.com/SurvivalGamesDevTeam/TheSurvivalGames/gh-pages/wiki/image/Features.png)](http://example.com) [![Commands](https://raw.githubusercontent.com/SurvivalGamesDevTeam/TheSurvivalGames/gh-pages/wiki/image/Commands.png)](http://example.com) [![Permissions](https://raw.githubusercontent.com/SurvivalGamesDevTeam/TheSurvivalGames/gh-pages/wiki/image/Permissions.png)](http://example.com) [![Setup](https://raw.githubusercontent.com/SurvivalGamesDevTeam/TheSurvivalGames/gh-pages/wiki/image/Setup.png)](http://example.com) [![Website](http://raw.githubusercontent.com/SurvivalGamesDevTeam/TheSurvivalGames/gh-pages/wiki/image/Website.png)](http://communitysurvivalgames.com)  [![dev.bukkit.org](http://raw.githubusercontent.com/SurvivalGamesDevTeam/TheSurvivalGames/gh-pages/wiki/image/DBO.png)](http://dev.bukkit.org/bukkit-plugins/the-survival-games/)

[![Features](http://imgur.com/F2MBj0Y.png)](https://github.com/SurvivalGamesDevTeam/Builds/tree/master/com/communitysurvivalgames/thesurvivalgames/TheSurvivalGames/1.0.0)  
Build Stats: [![Build Status](https://travis-ci.org/SurvivalGamesDevTeam/TheSurvivalGames.png?branch=master)](https://travis-ci.org/SurvivalGamesDevTeam/TheSurvivalGames)

======== 

**Announcements**

**[ANNOUNCEMENT|Quantum64]**
All developers should read the tutorial for developing on the new multilingual string system here:  https://github.com/SurvivalGamesDevTeam/TheSurvivalGames/blob/master/I18N_TUTORIAL.md

**[ANNOUNCEMENT|AgentTroll]**
Format is important: All classes should be based off of the Help SubCommand class with the titling comment and comments for each method. All indentations should be SPACES, not tabs. They should be 4 spaces, not 8 as well. **ALL CODE MUST BE IN JAVA 7**

**[ANNOUNCEMENT|Quantum64]**
Let's not use .*; for imports. If you're using eclipse, just Ctrl + Shift + o and it will auto clean up all your imports.

**[ANNOUNCEMENT|TheEpicButterStudios]**
We don't use @author. Ever. 

**[ANNOUNCEMENT|AgentTroll]**
Never use tabs.

**[ANNOUNCEMENT|TheEpicButterStudios]**
Try to edit with an IDE before putting something on GitHub as much as possible, but small tweaks directly on GitHub are fine.

=========

**Description**

What is there to describe? This will be a community project, this is the description.
Survival Games plugin cannot be boring, it has to have a lot of cool features that will make people want to play on it, so I have come up with this idea. The plugin is very similar to MCSG. I would also like it to be MultiWorld compatible if you can so it is more useful to other people.

========

**Signs**

First you have the join sign which will be laid out like so

```
Survival Games
<Arena Number>
<What state the game is in (e.g. Voting, pre-game, in-game and deathmatch)>
<Total players in the game (e.g. 3/24)>
```

Next, when you right click on the sign you will teleported to a location set with a command, your inventory will be cleared (and saved) and you are now in the lobby phase. You will not be able to break any blocks and you will be able to do /sg vote <arena number> (e.g. /sg vote 2), which will create your vote for the map number 2, if it is possible the map number (e.g. 2) will have the map name next to it (e.g. 2 - Teweran Survival Games 3 (Votes: 4)) In the end, the map with the most number of votes will win and will be selected to be played. If there isn't another players in the lobby it will restart but the votes/maps will be kept (amount of min players could be configurable per arena/lobby in config or with an in-game command). People in the lobby, pre-game, in-game and deathmatch will be able to do /sg records to see their total kills, deaths, wins and points (I'll get onto points later).

========

**Map**

When the map has been selected to be played, you will be teleported to one of the spawnpoints (set with a command) and you will not be able to move until the configurable time (default 45 seconds) has past. In the time all the chests on the map will generate random items (I'll talk later about chests), once the time is up the game timer will start and players will be able to run and collect items from chests, every minute a message will pop-up saying how long is left in minutes (each game is 30 mins long then deathmatch will start if there is still more than 4 players) and how many players are left (players can type /sg list to see who is in game and the message will be displayed as so in this example):

```
Alive: Player1, Player2, Dave1234, Jimmy1235, Yebol, SteveMC, ImSoPr0.
Dead: ImBadAtPvP, SamHaribo, Isuck12, QuantumTiger, JamkoMC, bob987.
Spectating: SamHaribo, bob987, RandomGuy6, JamkoMC.
```

Chests will refill with items at 17 minutes through the game, with the same Tier (i'll talk about them in a minute) items. When in the arena the only blocks you will be able to break are, flowers, long grass, mushrooms, and leaves.

========

**Winning and Deathmatch**

Once the 30 minutes are up or their are 4 players left alive the deathmatch countdown will begin from 60, if all but one players are killed then the remaining player wins and the game ends, if more than 1 player is still alive when the deathmatch counter has ended both players will be sent to the deathmatch arena on different spawnpoints where they will fight to the death and one will become the victor.
The game ends and players will be teleported back to where they were when they first clicked the sign and will be given the items they had in their inventory again.

========

**Extra Info**

Chests will have 2 Tiers, Tier 1 (T1) chests and Tier 2 (T2) chests.

Tier 1s will contain items like: leather armour pieces, gold armour pieces, wooden swords and axes, stone swords and axes, un-cooked food like cookies, apples, carrots, pumpkin pie, raw chicken, raw steak, raw fish (include 1.7 fish if you want to as well), the chests will also have items such as sticks x2 (not very common), feathers x3, bones x2, fishing rods (not very common), flint (not very common), arrows x4 (not very common), a gold ingot (not very common), wheat x2, flint and steel x1 (not very common), and an iron ingot (not very common). The T1s will also include the Extra items which you can find lower down the page. TNT will be a not very common item, Sugar rush will also be a not very common item and also the slowball which will be quite common but not so that it is OP.

Tier 2s will contain rarer items like: diamonds (maximum of 5 in a game), iron armour pieces, chain armour pieces, regular golden apple, the care package, cooked food, cake, and 1 or 2 tier 1 items as well e.g. arrows will be more common in T2 than in T1.

When setting up the arenas all chests will be T1s by default and doing the /sg setchest t2 <arena number> while looking at a chest will turn it into a T2 chest. (cannot be done ingame)

Spectating will hopefully be a feature, when a player dies they respawn where they died with flying enabled, they wont be able to attack people/knockback, they will be invisible to everyone else, no health (other players, fall damage and fire damage can't kill them) or hunger can be lost and a compass in their inventory, the compass, when right-clicked will open up a GUI with players heads in them (which can't be taken out) and when right clicked the spectator will teleport to the person they have selected.

========

**Points**

Points will be gained when you get a kill (customise amount in config) and winning a game (customise amount in config), you can lose points by dying (customise amount in config) and you can spend points either bountying them (see below) on people or sponsoring other players (again, see below). Your points will be displayed before your name like this example when you are in the lobby stage or before you have even joined a game:

```696 : DaveMan123: Hi, I have 696 points, lol that is funny! :D```

After the lobby stage (pre-game, in-game and deathmatch), your name will look like this and the number you have before your name will be the amount of points bountied on you.

```50 : DaveMan123: LOL! someone bountied 50 points because I killed them!```

========

**Parties**

Parties can be used to chat with your friends, and join the same games, without having to rush click the same sign.  The list of command of using and creating parties are as follows:

```/party invite : Invites a player to your party```

```/party join : Joins the party you were invited to```

```/party leave : Leaves your current party```

```/party decline: Declines your current party invite```

```/party list: Displays your current party members```

```/party kick: Removes a player from your party```

```/party promote: Promotes another player to party leader```

```/party chat: Toggles whether or not to speak in party chat only```


========

**Bountying**

Bountying will be able to happen throughout the game by players and spectators, when a player types /sg bounty <playername> <amount of points being bountied> the points will be taken from their profile and put on the other players name (see above Points section). When a player with a bounty on them is killed, the killer will receive the points added onto the points gained from killing a player. If the player does not die in the whole game then they will get the points added onto their points total.

========

**Sponsoring**

When you are spectating a game you can sponsor a player with /sg sponsor <playername> <amount of points you sponsor>. Also a sponsoring group e.g. 1-10 can only be done once per game so if 1 person does /sg sponsor dave12 8 and then another person does /sg sponsor dave1235678 9 then the 2nd person will get a message like this: Sorry. Someone has already been sponsored with an amount of points between 1-10. Try sponsoring more points or leave the player to fight by themselves! I will make a little table thing to show how the points you sponsor help.

1-15 points = food, if more points are given the person will recvieve better items e.g. 5 points = raw steak, chicken, apple 13 points = steak, porkchop, cake. (if 15 points are sponsored the player will have a 1/7 chance of receiving a regular golden apple)
16-30 points = extras (see below), arrows, tnt, sugar, etc. (the more points sponsored the more items the player will get e.g. 19 points gives 4 arrows, 28 points gives 9) 31-45 points = useful items, flint and steel, iron ingots, chain armour, diamond (1/7 chance if 45 points are sponsored), iron armour (1/10 chance if 45 points are sponsored).

Extra Items
The extra items I have thought up of and nicked from other great servers are,

TNT which when placed on the floor instantly lights up and explodes after 2-4 seconds.

========

**Kits and Items**

Sugar (named Sugar rush) which when right clicked, slows you down like you are eating it for same amount of time as normal food and then gives you a speed 1 boost for 10 seconds, great for outrunning people.

Another item is the slowball, which is a snowball (renamed slowball) that can be used to hit the targeted player and it gives them slowness 1 for 20 seconds, which allows players to chase them down and hopefully get the kill.

Care Package, a renamed firework which when right-clicked sends a firework up into the air where it explodes constantly for 10 seconds so other players are alerted to the users position. After the 10 seconds, a random T2 item will drop from the sky to the location that the care package was set off.

=========

**Commands**

```
/sg create (creates arena)

/sg remove <arena number> (removes an arena)

/sg setlobby <arena number> (sets lobby spawn point)

/sg setgamespawn <spawn point number> (max can be set in config, default 48)> <arena number>

/sg setdeathmatch <spawn point number> (max can be set in config, default 48)> <arena number>

/sg setmaxplayers <max players> <arena number>

/sg setchest t1 / t2 <arena number> (set chest tiers, by default all are t1s)

/sg join <arena number> (only available before joining any other game)

/sg vote <map number> (vote for map in lobby phase only)

/sg bounty <player> <points> (set a bounty on a player)

/sg sponsor <player> <points> (sponsor a player with items)

/sg leave (leave an arena)

/sg start <arena number> <the state to put the game into> (force starts the state the game needs to go into e.g. player is in-game and does /sg forcestart 2 dm for a deathmatch force start)

/sg stop <arena number> (force stops a game)
```
========

**Permissions**

```
sg.admin (access to all survivalgames.player commands and survivalgames.mod commands as well as the arena setup commands)

sg.mod (access to all survivalgames.player commands and also /sg forcestart and /sg forcestop)

sg.player (access to joining arenas, showing available arenas, voting for maps, spectating, boutying and sponsoring)

sg.vote.worth.<number> (how much a vote is worth, survivalgames.player will have 1 by default but it can be given to other players for example donators so that they can have a better chance of having their map selected)

sg.join.<arena name> (permission to join an arena, when set to false players cannot join the arena (useful for donator only arenas + staff only testing)

sg.join.* (access to join all arenas)

sg.create (create an arena)
```

========

**SGDevTeam in the Social Media**

***Twitter*** : https://twitter.com/SGDevTeam (**@SGDevTeam**)

***Instagram*** : http://instagram.com/SGDevTeam#

========

**Get Involved**

You can PM someone on BukkitDev:

Quantum64 **(Recommended)** ``` http://dev.bukkit.org/profiles/Quantum64/ ```

TheEpicButterStudios ``` http://dev.bukkit.org/profiles/TheEpicButterStudios/ ```

Trolldood3 (AgentTroll) ``` http://dev.bukkit.org/profiles/Trolldood3/ ```

Relicum ``` http://dev.bukkit.org/profiles/Relicum/ ```

Email us :

**survivalgamesdevteam@gmail.com**

Or visit our website, **http://communitysurvivalgames.com/**

Start developing ! See you then !


