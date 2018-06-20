# LibertyCord

**Another fork of BungeeCord**

## What is LibertyCord?

LibertyCord is a Layer 7 proxy for _Minecraft: Java Edition_ servers, forked from [BungeeCord](https://github.com/SpigotMC/BungeeCord).
It is designed to handle many hundreds or thousands of connections concurrently for a virtually unlimited number of servers.

## Why did you start this project?

LibertyCord was borne out of the desire to improve BungeeCord in ways that I feel are better suited for Minecraft development.
The changes I plan to make are unlikely to be accepted by md\_5, the author of BungeeCord.

I had been involved in another BungeeCord fork, Waterfall, which ended up being a PaperSpigot analogue to BungeeCord. Waterfall is today mostly aimed at people who desire strong Forge support.

LibertyCord is expressly aimed at three types of people:

* **People where efficiency counts most**. LibertyCord aims for a more compact codebase while not compromising on performance
  and scalability.
* **People who want a fresh development experience**. LibertyCord will introduce new APIs to facilitate new ways of developing
  against BungeeCord.
* **People looking to remove unpredictable behavior**. With LibertyCord, we aim to not introduce any "surprises", and in fact
  aim to eliminate them where possible.

### What are your focuses?

Here's what we focus on:

* API changes to improve the development experience.
* Removing features that have clearly failed in their purpose.
* Simplifying the code base.
* Introducing performance enhancements.
* Attempts to remain compatible with vanilla BungeeCord where possible.
  
Here's what we won't focus on:

* Compatibility for old MC versions. Please upgrade to the latest MC version.