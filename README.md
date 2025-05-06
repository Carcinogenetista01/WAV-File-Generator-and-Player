# 🔊 WAV File Generator and Player

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Audio](https://img.shields.io/badge/Audio-FF5500?style=for-the-badge&logo=audio-technica&logoColor=white)

Java utilities for generating and playing WAV files with sine wave signals.

## 📦 Project Structure

```
src/
├── EjecutaWAV.java       # WAV file player and generator from input file
└── GeneraWAV.java        # WAV file generator with sine waves
```

## 🎵 EjecutaWAV - WAV Player/Generator

### Features
- Generates WAV files from input parameters
- Plays generated WAV files
- Supports custom:
  - Sample rates
  - Frequencies
  - Durations

### Usage
```bash
java EjecutaWAV input.txt
```

### Input File Format
```
output.wav     # Output filename
44100          # Sample rate (Hz)
440            # Frequency (Hz)
5              # Duration (seconds)
```

## 🎹 GeneraWAV - WAV Generator

### Features
- Generates sine wave WAV files
- Proper WAV header implementation
- Configurable:
  - Frequency
  - Duration
  - Sample rate
  - Harmonic selection

### Usage
```java
GeneraWAV generador = new GeneraWAV();
generador.escribe("output.wav", duration, sampleRate, frequency);
```

### Example
```java
// Generate 5-second 440Hz A4 tone at 44.1kHz
generador.escribe("A4.wav", 5, 44100, 440);
```

## 🛠️ Technical Details

### WAV File Format
- PCM format
- 16-bit samples
- Mono channel
- Proper RIFF/WAVE headers
- Little-endian byte order

### Audio Generation
- Pure sine wave generation
- Proper sample scaling to 16-bit range
- Accurate frequency generation

## 📝 Requirements

- Java 8+
- No external dependencies

## 🚀 Getting Started

1. Compile the Java files:
```bash
javac *.java
```

2. Create an input file (for EjecutaWAV):
```bash
echo -e "output.wav\n44100\n440\n5" > input.txt
```

3. Run the generator/player:
```bash
java EjecutaWAV input.txt
```

Or use GeneraWAV directly:
```bash
java GeneraWAV
```

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---

Developed with Java  
Audio generation utilities for educational purposes
