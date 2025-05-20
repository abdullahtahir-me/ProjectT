
# 🕹️ 2D Destructible Terrain Game

A 2D physics-based game built with **Java** and **libGDX**, featuring realistic **projectile mechanics**, **destructible terrain**, and **collision detection using heightmaps**. Inspired by classic artillery and sandbox games, this project blends physics, terrain manipulation, and creative gameplay.

---

## 🚧 Project Status

🚀 In active development  
🧪 Alpha-level features being tested  
🎯 Core mechanics implemented: terrain destruction, projectile dynamics, basic collision

---

## 🎮 Features

- 🧱 **Destructible Terrain**: Modify the terrain in real-time using explosions or tools.
- 💣 **Projectile Physics**: Simulates trajectory, gravity, and collision using simple physics.
- 🌄 **Heightmap Collision**: Terrain defined by a heightmap and used for physics interactions.
- ⚙️ **libGDX Framework**: Cross-platform support for Desktop, Android, and HTML5.
- 🎨 **Pixel-Based Terrain Rendering**: Smooth dynamic updates as the terrain changes.
- 🔄 **Regenerating/Resettable Terrain**: Easily reloadable for testing or gameplay loops.

---

## 🛠️ Built With

- **Java 8+**
- **[libGDX](https://libgdx.com/)** – Game development framework
- **Gradle** – Build automation

---

## 🧑‍💻 Getting Started

### Prerequisites

- Java JDK 8 or newer
- [Gradle](https://gradle.org/install/)
- Git (for cloning the repo)
- libGDX setup (you can use the official [setup tool](https://libgdx.com/dev/setup/))

### Installation

```bash
# Clone the repository
git clone https://github.com/yourusername/destructible-terrain-game.git
cd destructible-terrain-game

# Run the game
./gradlew desktop:run
```

### Project Structure

```
.
├── core/             # Core game logic (platform-agnostic)
│   ├── Terrain/      # Terrain generation & modification
│   ├── Physics/      # Projectile physics & collision logic
│   └── Renderer/     # Rendering and asset loading
├── desktop/          # Desktop launcher
├── android/          # Android platform target (optional)
├── assets/           # Textures, heightmaps, sound files
└── build.gradle      # Gradle build configuration
```

---



## 🧪 Testing

You can run unit tests (if available) using:

```bash
./gradlew test
```

For rendering and physics tests, consider using an in-game debug mode or simulation log output.

---

## 🧠 Implementation Details

### Destructible Terrain

- Implemented using a 2D pixel grid or heightmap.
- Projectiles subtract from terrain when colliding.
- Terrain updated using a mask or brush (circular explosion).
- Terrain stored as a Pixmap or texture array and re-rendered each frame.

### Projectile Physics

- Uses basic kinematics: `position += velocity * deltaTime`, `velocity += gravity * deltaTime`
- Custom collision detection with terrain edge from heightmap or pixel data

### Terrain Collision

- Heightmap stored as an array of Y-values for each X-column
- Projectiles compare their Y-position to the terrain height for collision detection
- Optionally use Box2D polygon shapes for dynamic terrain representation

---

## 🗺️ Roadmap

- [x] Terrain destruction
- [x] Heightmap-based collision
- [x] Custom projectile physics
- [x] Particle effects for explosions
- [ ] Enemy AI / target bots
- [ ] Scoring & game rounds
- [ ] Multiplayer / hotseat mode
- [ ] Mobile support (Android)

---

## 🤝 Contributing

Contributions are welcome! Here's how:

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/something`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/something`
5. Submit a pull request

Please include screenshots or videos for visual features!


---

## 📬 Contact

**Abdullah Tahir** – [abdullahkbintahir@gmail.com](mailto:abdullahkbintahir@gmail.com)  
**Abdul Rehman** – [abdulr098123@gmail.com](mailto:abdulr098123@gmail.com)  
Project Link – [https://github.com/abdullahtahir-me/ProjectT](https://github.com/abdullahtahir-me/ProjectT)

---

## 🙏 Acknowledgments

- [libGDX Community](https://discord.gg/libgdx)
- Inspiration from **Worms**, **Pocket Tanks**, **Scorched Earth**, and **Terraria**
- Thanks to all testers and contributors!
