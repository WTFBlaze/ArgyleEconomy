# ArgyleEconomy
An open sourced economy plugin designed to work and be compatible with Essentials &amp; PlaceholderAPI with an API for developers to hook into.

# Installation
- Shut down server
- place `ArgyleEconomy-Release-VERSION.jar` into your plugins folder.
- Start start.
- Enjoy

# Commands
`/aeco` - help menu & admin commands
`/apay` - pay another player
`/abal` - view your own or another players balance
`/aeco set` - set a players balance
`/aeco reset` - reset a players balance back to 0

# Permissions
`argyle_econ.*` - gives every permission in the plugin (Only recommended for testing)
`argyle_econ.user` - gives user focused permissions
`argyle_econ.admin` - gives admin focused permissions
`argyle_econ.pay` - pay another player
`argyle_econ.balance` - view your own balance
`argyle_econ.balance.others` - view other users balance
`argyle_econ.eco` - view the basic help command
`argyle_econ.set` - set another players balance
`argyle_econ.reset` - reset a players balance back to 0

# Placeholders
`%argyleecon_balance%` - get the targets balance. Result: `$10.0`
`%argyleecon_balance_exlude_trail%` - return balance without cents value. Result: `$10`
`%argyleecon_balance_exclude_symbol%` - returns balance without currency symbol. Result: `10.0`
`%argyleecon_balance_exclude_symbol_trail%` - returns only the whole dollar value. Result: `10`

# Developer API
[Maven]
```xml
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
...
<dependency>
	    <groupId>com.github.WTFBlaze</groupId>
	    <artifactId>ArgyleEconomy</artifactId>
	    <version>-SNAPSHOT</version>
	</dependency>
```

[Gradle]
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
...
dependencies {
	        implementation 'com.github.WTFBlaze:ArgyleEconomy:-SNAPSHOT'
	}
```

All functions are inside of `me.wtfblaze.ArgyleEconomyAPI`.
Methods:
- getBalance | Params: OfflinePlayer
- setBalance | Params: OfflinePlayer, double
