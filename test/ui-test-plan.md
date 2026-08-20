# Computa UI test plan

- Program command: `java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp _temp/ui-test-classes Computa`
- Build command: `javac -encoding UTF-8 -d _temp/ui-test-classes src/main/java/*.java`
- Working directory: `.`
- Timeout seconds: `10`

Each test case starts a fresh program session. Output comparison is exact apart
from platform line endings and a final trailing newline.

## Test case: exit immediately

### Aim

Verify that Computa prints its greeting and exits with the requested farewell.

### Inputs

```text
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: invalid input does not add a task

### Aim

Verify that an unknown command is rejected, while a following valid todo is stored and numbered correctly.

### Inputs

```text
blah
todo read book
list
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
Hmph! Making small talk won't get you anywhere.  ʕ ꈍᴥꈍʔ
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [T][ ] read book
Now you have 1 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
We've got so much to do (⋟﹏⋞)
Hmph! I guess I'll have to spend more time with you (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)
1.[T][ ] read book
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: add and list a todo

### Aim

Verify that a todo is stored and displayed with the todo type and incomplete status.

### Inputs

```text
todo borrow book
list
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [T][ ] borrow book
Now you have 1 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
We've got so much to do (⋟﹏⋞)
Hmph! I guess I'll have to spend more time with you (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)
1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: deadline and event details

### Aim

Verify that deadline and event date/time strings are stored and shown without date conversion.

### Inputs

```text
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [D][ ] return book (by: Sunday)
Now you have 1 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 2 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
We've got so much to do (⋟﹏⋞)
Hmph! I guess I'll have to spend more time with you (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)
1.[D][ ] return book (by: Sunday)
2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: invalid commands

### Aim

Verify that missing todo descriptions and unknown commands produce friendly error messages without terminating the session.

### Inputs

```text
todo
blah
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
Hmph! This is just an excuse to hang out with me, right?
Who says you get to spend empty time with me? ୧( ˵ ° ~ ° ˵ )୨
____________________________________________________________
____________________________________________________________
Hmph! Making small talk won't get you anywhere.  ʕ ꈍᴥꈍʔ
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: malformed task commands

### Aim

Verify that malformed deadline, event, and task-status commands are handled without crashing.

### Inputs

```text
deadline return book
event project meeting /from Monday
mark
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
A deadline needs a description and a /by date or time.
____________________________________________________________
____________________________________________________________
An event needs a description, /from date or time, and /to date or time.
____________________________________________________________
____________________________________________________________
Please provide a valid task number.
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: malformed date commands do not add tasks

### Aim

Verify that malformed deadlines and events are rejected before valid deadline and event commands are stored.

### Inputs

```text
deadline report
deadline report /by Friday
event meeting /from 2pm
event meeting /from 2pm /to 3pm
list
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
A deadline needs a description and a /by date or time.
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [D][ ] report (by: Friday)
Now you have 1 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
An event needs a description, /from date or time, and /to date or time.
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [E][ ] meeting (from: 2pm to: 3pm)
Now you have 2 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
We've got so much to do (⋟﹏⋞)
Hmph! I guess I'll have to spend more time with you (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)
1.[D][ ] report (by: Friday)
2.[E][ ] meeting (from: 2pm to: 3pm)
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: invalid status indexes preserve task state

### Aim

Verify that invalid `mark` and `unmark` indexes do not change a valid task's completion state.

### Inputs

```text
todo read book
mark 2
mark 1
unmark 3
unmark 1
list
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [T][ ] read book
Now you have 1 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
Please provide a valid task number.
____________________________________________________________
____________________________________________________________
Yatta! (ᗒᗨᗕ) I knew you could do it (✧ᴗ✧✿) 

  [X] read book
____________________________________________________________
____________________________________________________________
Please provide a valid task number.
____________________________________________________________
____________________________________________________________
Gambare, Goshujin-Sama ! ˚‧º·( 。ᗒ ‸ ◕✿) 

  [ ] read book
____________________________________________________________
____________________________________________________________
We've got so much to do (⋟﹏⋞)
Hmph! I guess I'll have to spend more time with you (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)
1.[T][ ] read book
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```

## Test case: delete and renumber tasks

### Aim

Verify that deleting a valid task removes it, renumbers the remaining tasks, and rejects an invalid delete index without changing the list.

### Inputs

```text
todo read book
todo return book
list
delete 1
list
delete 5
bye
```

### Expected output

```text
____________________________________________________________
                         COMPUTA
Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°
I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)
What can I do for you?
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [T][ ] read book
Now you have 1 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७
  [T][ ] return book
Now you have 2 tasks in the list. (⋟﹏⋞)
(.づ◡﹏◡)づ. When will we get some alone time together?
____________________________________________________________
____________________________________________________________
We've got so much to do (⋟﹏⋞)
Hmph! I guess I'll have to spend more time with you (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)
1.[T][ ] read book
2.[T][ ] return book
____________________________________________________________
____________________________________________________________
Goshujin-Sama, you don't want to do this with me anymore? (๑˃̣̣̥⌓˂̣̣̥)
  [T][ ] read book
Now you have 1 tasks in the list.
Not that I want to hang out with you anyway. (๑•́ ₃ •̀๑)
____________________________________________________________
____________________________________________________________
We've got so much to do (⋟﹏⋞)
Hmph! I guess I'll have to spend more time with you (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)
1.[T][ ] return book
____________________________________________________________
____________________________________________________________
Please provide a valid task number.
____________________________________________________________
____________________________________________________________
Noooo don't go!!! Hmph. Fine... Hope to see you again soon!
____________________________________________________________
```
