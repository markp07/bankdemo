#!/usr/bin/env bash
docker system prune
docker compose build --no-cache
docker compose up -d