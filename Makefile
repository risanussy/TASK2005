# Makefile di project root (di samping folder src)
SRC := $(shell find src -type f -name '*.java')
BIN := bin

.PHONY: all clean tests run

all: $(BIN)
	javac -d $(BIN) $(SRC)

$(BIN):
	mkdir -p $(BIN)

clean:
	rm -rf $(BIN)

tests: all
	@echo "=== MoveTest ==="
	java -cp $(BIN) game.tests.MoveTest
	@echo "=== GridTest ==="
	java -cp $(BIN) game.tests.GridTest
	@echo "=== GameTest ==="
	java -cp $(BIN) game.tests.GameTest

run: all
	java -cp $(BIN) ai.PlayVsAI
