import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

// Command Interface
interface Command {
    void execute();
}

// Move Command
class MoveCommand implements Command {
    private Rover rover;

    public MoveCommand(Rover rover) {
        this.rover = rover;
    }

    @Override
    public void execute() {
        rover.move();
    }
}

// Turn Left Command
class TurnLeftCommand implements Command {
    private Rover rover;

    public TurnLeftCommand(Rover rover) {
        this.rover = rover;
    }

    @Override
    public void execute() {
        rover.turnLeft();
    }
}

// Turn Right Command
class TurnRightCommand implements Command {
    private Rover rover;

    public TurnRightCommand(Rover rover) {
        this.rover = rover;
    }

    @Override
    public void execute() {
        rover.turnRight();
    }
}

class Rover {
    private int x, y;
    private char direction;
    private Set<String> obstacles;

    public Rover(int x, int y, char direction, Set<String> obstacles) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.obstacles = obstacles;
    }

    public void move() {
        int newX = x, newY = y;
        switch (direction) {
            case 'N': newY++; break;
            case 'E': newX++; break;
            case 'S': newY--; break;
            case 'W': newX--; break;
        }

        if (isWithinBounds(newX, newY) && !isObstacle(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    public void turnLeft() {
        switch (direction) {
            case 'N': direction = 'W'; break;
            case 'E': direction = 'N'; break;
            case 'S': direction = 'E'; break;
            case 'W': direction = 'S'; break;
        }
    }

    public void turnRight() {
        switch (direction) {
            case 'N': direction = 'E'; break;
            case 'E': direction = 'S'; break;
            case 'S': direction = 'W'; break;
            case 'W': direction = 'N'; break;
        }
    }

    public String statusReport() {
        String directionFullName = "";
        switch (direction) {
            case 'N': directionFullName = "North"; break;
            case 'E': directionFullName = "East"; break;
            case 'S': directionFullName = "South"; break;
            case 'W': directionFullName = "West"; break;
        }
        return "Rover is at (" + x + ", " + y + ") facing " + directionFullName + ". No Obstacles detected.";
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    private boolean isObstacle(int x, int y) {
        return obstacles.contains(x + "," + y);
    }
}

public class MarsRoverSimulation {

    public static void executeCommands(Rover rover, List<Command> commands) {
        for (Command command : commands) {
            command.execute();
        }
        System.out.println(rover.statusReport());
    }

    public static void main(String[] args) {
        Set<String> obstacles = new HashSet<>(Arrays.asList("2,2", "3,5"));
        Rover rover = new Rover(0, 0, 'N', obstacles);

        List<Command> commands = Arrays.asList(
                new MoveCommand(rover),
                new MoveCommand(rover),
                new TurnRightCommand(rover),
                new MoveCommand(rover),
                new TurnLeftCommand(rover),
                new MoveCommand(rover)
        );

        executeCommands(rover, commands);
    }
}
