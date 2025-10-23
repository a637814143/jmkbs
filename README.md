# Database Connection Helper

This repository provides a small Python helper for connecting to the `jmk_movie`
MySQL database using the credentials:

- **User:** `root`
- **Password:** `123456`

The helper centralises configuration, supports environment variable overrides,
and ships with a simple command-line script to verify that the credentials work.

## Prerequisites

- Python 3.9 or later
- Access to the target MySQL server

Install dependencies:

```bash
pip install -r requirements.txt
```

## Usage

The quickest way to confirm connectivity is by running the test script:

```bash
python -m src.test_connection
```

By default it uses the host `localhost`, port `3306`, database `jmk_movie`, user
`root`, and password `123456`.  Override any of these values by supplying
command-line flags or environment variables:

```bash
export DB_HOST=database.internal
export DB_PASSWORD=changeme
python -m src.test_connection --database jmk_movie
```

Within your own application you can create a connection using:

```python
from src import create_connection

connection = create_connection()
```

The `create_connection` function accepts the same keyword arguments as the CLI
(`host`, `port`, `database`, `user`, `password`) if you want to provide explicit
values in code.
