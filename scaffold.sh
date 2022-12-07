#!/bin/bash
#
# Scaffold New Day
#

set -u

# needs to be in 2 digit format: '01'
DAY_NUMBER="$1"
SRC_PATH="src"
TEMPLATE_PATH="Template.txt"

function create_input_files() {
    (
      cd "$SRC_PATH" || exit
      touch "Day$DAY_NUMBER.txt"
      touch "Day${DAY_NUMBER}_test.txt"
      create_code_file
    )
}

function create_code_file() {
    CODE_FILE="Day$DAY_NUMBER.kt"
    cp "../$TEMPLATE_PATH" "$CODE_FILE"
    sed -i "s/Template/Day$DAY_NUMBER/" "$CODE_FILE"
}

echo "Creating Files for Day$DAY_NUMBER"
create_input_files