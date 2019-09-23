# Robots

## Development

To check for dependency updates:

    mvn versions:display-dependency-updates

Generate protoc classes

    protoc --proto_path=res --java_out=src res/anki_vector/messaging/*.proto res/google/api/*.proto
