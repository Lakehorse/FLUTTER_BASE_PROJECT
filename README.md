
# Horizen Sidechain SDK notes experiment

![A chain made of paper](./img/header.jpeg "A chain made of paper")

_A chain made of paper_

---

Application specific sidechain node to manage public notes.

This application was built using the [Horizen Sidechain SDK](https://docs.horizen.io/en/latest/), a set of tools to create sidechains for the [Horizen](https://www.horizen.io) network.

## I don't understand the implemenetation 😦

The project is organized in different modules for different purposes. The more relevant ones are:

- **`boxes`**. The representation of the business model entities and its data, as Unspent Transaction Outputs (UTXOs).
- **`transactions`**. The custom transactions created to follow the specification of the application's business model.
- **`core`**. The core components of a sidechain node: `ApplicationState`, `ApplicationWallet` and `ApplicationModule`.
- **`api`**. Extended API components, requests and response.

## How do I execute a node? 🤯

1. [Install IntelliJ Idea](https://www.jetbrains.com/idea/) 💿 or [Java JDK 11](https://www.oracle.com/java/technologies/downloads/) and [Kotlin](https://kotlinlang.org/docs/getting-started.html).
2. Clone this repository 👭🏻.
3. Open the project 🖥 and install the dependencies 💿.
4. Execute the node by running the app from IntelliJ (▶️) or run the following command in your terminal `docker run -p 9084:9084 9085:9085 8025:8025 sidechain-node` ⛓😋.
5. After building and initializing, you can access the API documentation through the `localhost:9085/swagger#/` endpoint 😳.
6. You can also execute operations using the generated CLI tool (`notesd`) 🥶.